package com.example.koifarm.service;

import com.example.koifarm.entity.*;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.enums.PaymentEnums;
import com.example.koifarm.enums.Role;
import com.example.koifarm.enums.TransactionEnum;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.OrderDetailRequest;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BatchKoiRepository batchKoiRepository;

    @Transactional
    public Orders create(OrderRequest orderRequest) {
        User customer = authenticationService.getCurrentUser();
        Orders order = new Orders();
        List<OrderDetails> orderDetails = new ArrayList<>();
        float total = 0;

        order.setCustomer(customer);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatusEnum.PENDING);

        for (OrderDetailRequest orderDetailRequest : orderRequest.getDetail()) {
            if (orderDetailRequest.isBatch()) {
                // Xử lý lô cá
                BatchKoi batchKoi = batchKoiRepository.findById(orderDetailRequest.getItemId())
                        .orElseThrow(() -> new IllegalArgumentException("Batch Koi not found"));

                if (!batchKoi.isAvailable()) {
                    throw new IllegalStateException("Batch Koi is no longer available.");
                }

                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setQuantity(orderDetailRequest.getQuantity());
                orderDetail.setPrice(batchKoi.getBatchKoiPrice() * orderDetailRequest.getQuantity());
                orderDetail.setOrders(order);
                orderDetail.setBatchKoi(batchKoi);

                orderDetails.add(orderDetail);
                total += batchKoi.getBatchKoiPrice() * orderDetailRequest.getQuantity();

                batchKoi.setAvailable(false); // Đánh dấu lô cá đã bán
                batchKoiRepository.save(batchKoi);

            } else {
                // Xử lý cá thể
                Koi koi = koiRepository.findKoiByKoiID(orderDetailRequest.getItemId());
                if (!koi.getStatus().equals("available")) {
                    throw new IllegalStateException("Koi fish is no longer available.");
                }

                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setQuantity(1); // Mỗi cá thể chỉ có 1 số lượng
                orderDetail.setPrice(koi.getPrice());
                orderDetail.setOrders(order);
                orderDetail.setKoi(koi);

                orderDetails.add(orderDetail);
                total += koi.getPrice();

                koi.setStatus("sold");
                koiRepository.save(koi);
            }
        }

        order.setOrderDetails(orderDetails);
        order.setTotal(total);

        return orderRepository.save(order);
    }


    public List<Orders> get(){
        User user = authenticationService.getCurrentUser();
        List<Orders> orders = orderRepository.findOrdersByCustomer(user);

        // Sắp xếp theo thứ tự ngày từ nhỏ đến lớn
        orders.sort(Comparator.comparing(Orders::getDate));

        return orders;
    }

    public String createUrl(OrderRequest orderRequest) throws  Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //tao orders
        Orders orders = create(orderRequest);
        // Tính tổng tiền và thiết lập URL thanh toán
        float money = orders.getTotal()*100;
        String amount = String.valueOf((int) money);

        String tmnCode = "OHNZVMJ7";
        String secretKey = "KL9DNZY17C0XVP5QQI95KJJX49F2U71E";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:5173/success?orderID=" + orders.getId(); //return trang thanh toan thanh cong
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", orders.getId().toString()); //moi don hang la duy nhat
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + orders.getId());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount",amount);

        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }

    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    @Transactional(rollbackFor = { IllegalStateException.class, RuntimeException.class })

    public void createTransaction(UUID uuid){
        //tim order
        Orders orders = orderRepository.findById(uuid)
                .orElseThrow(()->new EntityNotFoundException("Order not found!"));

        // Kiểm tra trạng thái đơn hàng, nếu đã thanh toán rồi thì không cho thanh toán lại
        if (orders.getStatus() == OrderStatusEnum.COMPLETED) {
            throw new IllegalStateException("Order has already been paid.");
        }

        // Check if a payment already exists for this order
        if (orders.getPayment() != null) {
            throw new RuntimeException("Payment already exists for this order.");
        }

        //tao payment
        Payment payment = new Payment();
        payment.setOrders(orders);
        payment.setCreateAt(LocalDateTime.now());
        payment.setPayment_method(PaymentEnums.BANKING);
        payment.setTotal(orders.getTotal());

        paymentRepository.save(payment);

        Set<Transactions> transactionsSet = new HashSet<>();
        //tao transaction
        Transactions transactions1 = new Transactions();
        //VNPAY TO CUSTOMER
        User customer = authenticationService.getCurrentUser();
        transactions1.setFrom(null);
        transactions1.setTo(customer);
        transactions1.setPayment(payment);
        transactions1.setStatus(TransactionEnum.SUCCESS);
        transactions1.setDescription("NAP TIEN VNPAY TO CUSTOMER");
        transactionsSet.add(transactions1);

        Transactions transactions2 = new Transactions();
        //CUSTOMER TO MANAGER
        User manager = userRepository.findUserByRole(Role.MANAGER);
        transactions2.setFrom(customer);
        transactions2.setTo(manager);
        transactions2.setPayment(payment);
        transactions2.setStatus(TransactionEnum.SUCCESS);
        transactions2.setDescription("CUSTOMER TO MANAGER");
        float newBalance = manager.getBalance() + orders.getTotal()*0.1f;  //10% cua he thong
        transactions2.setAmount(newBalance);
        manager.setBalance(newBalance);
        transactionsSet.add(transactions2); //add to transacrionSet

        //MANAGER TO OWNER
        Transactions transactions3 = new Transactions();
        transactions3.setPayment(payment);
        transactions3.setStatus(TransactionEnum.SUCCESS);
        transactions3.setDescription("MANAGER TO OWNER");
        transactions3.setFrom(manager);
        User owner = orders.getOrderDetails().get(0).getKoi().getUser();
        transactions3.setTo(owner);
        float newShopBalance = owner.getBalance()+ orders.getTotal()*(1-0.1f);
        transactions3.setAmount(newShopBalance);
        owner.setBalance(newShopBalance);
        transactionsSet.add(transactions3);

        // Save payment and transactions
        payment.setTransactions(transactionsSet);

        orders.setPayment(payment); // Set the payment for the order
        orderRepository.save(orders); // Save the order with the payment

        userRepository.save(manager);
        userRepository.save(owner);
        paymentRepository.save(payment);

    }

    //Update Status
    public Orders updateOrderStatus(UUID orderId, OrderStatusEnum status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        // Chỉ có thể cập nhật thành COMPLETE nếu trạng thái hiện tại chưa phải là COMPLETE
        if (order.getStatus() == OrderStatusEnum.COMPLETED) {
            throw new IllegalStateException("Order is already complete and cannot be updated.");
        }

        order.setStatus(status);
        return orderRepository.save(order);
    }


}