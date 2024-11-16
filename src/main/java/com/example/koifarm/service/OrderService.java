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
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrders(order);
            orderDetail.setQuantity(orderDetailRequest.getQuantity());

            if (orderDetailRequest.isBatch()) {
                // Xử lý lô cá
                total += processBatchKoi(orderDetailRequest, orderDetail);
            } else {
                // Xử lý cá thể
                total += processIndividualKoi(orderDetailRequest, orderDetail);
            }

            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    private float processBatchKoi(OrderDetailRequest orderDetailRequest, OrderDetails orderDetail) {
        BatchKoi batchKoi = batchKoiRepository.findById(orderDetailRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Batch Koi not found"));

        if (!batchKoi.isAvailable()) {
            throw new IllegalStateException("Batch Koi is no longer available.");
        }

        // Gán giá trị cho itemType
        orderDetail.setItemType("batch");  // Gán giá trị cho itemType
        orderDetail.setPrice(batchKoi.getPrice() * orderDetailRequest.getQuantity());

        // Chờ thanh toán hoàn tất để cập nhật trạng thái
        orderDetail.setStatus("pending"); // Đặt trạng thái đơn hàng là 'pending'

        batchKoiRepository.save(batchKoi);

        return batchKoi.getPrice() * orderDetailRequest.getQuantity();
    }

    private float processIndividualKoi(OrderDetailRequest orderDetailRequest, OrderDetails orderDetail) {
        Koi koi = koiRepository.findKoiByKoiID(orderDetailRequest.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Koi not found"));

        if (!koi.getStatus().equals("available")) {
            throw new IllegalStateException("Koi fish is no longer available.");
        }

        // Gán giá trị cho itemType
        orderDetail.setItemType("individual");  // Gán giá trị cho itemType
        orderDetail.setPrice(koi.getPrice());

        // Chờ thanh toán hoàn tất để cập nhật trạng thái
        orderDetail.setStatus("pending"); // Đặt trạng thái đơn hàng là 'pending'

        koiRepository.save(koi);

        return koi.getPrice();
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
    public void createTransaction(UUID uuid) {
        // Lấy đơn hàng từ repository
        Orders orders = orderRepository.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        // Kiểm tra trạng thái đơn hàng, nếu đã thanh toán rồi thì không cho thanh toán lại
        if (orders.getStatus() == OrderStatusEnum.COMPLETED) {
            throw new IllegalStateException("Order has already been paid.");
        }

        // Kiểm tra xem đã có payment chưa
        if (orders.getPayment() != null) {
            throw new RuntimeException("Payment already exists for this order.");
        }

        // Tạo mới payment
        Payment payment = new Payment();
        payment.setOrders(orders);
        payment.setCreateAt(LocalDateTime.now());
        payment.setPayment_method(PaymentEnums.BANKING);
        payment.setTotal(orders.getTotal());

        paymentRepository.save(payment);

        Set<Transactions> transactionsSet = new HashSet<>();

        // Tạo transaction từ VNPAY cho khách hàng
        Transactions transactions1 = new Transactions();
        User customer = authenticationService.getCurrentUser();
        transactions1.setFrom(null);
        transactions1.setTo(customer);
        transactions1.setPayment(payment);
        transactions1.setStatus(TransactionEnum.SUCCESS);
        transactions1.setDescription("NAP TIEN VNPAY TO CUSTOMER");
        transactionsSet.add(transactions1);

        // Tạo transaction từ khách hàng đến quản lý
        Transactions transactions2 = new Transactions();
        User manager = userRepository.findUserByRole(Role.MANAGER);
        transactions2.setFrom(customer);
        transactions2.setTo(manager);
        transactions2.setPayment(payment);
        transactions2.setStatus(TransactionEnum.SUCCESS);
        transactions2.setDescription("CUSTOMER TO MANAGER");
        float newBalance = manager.getBalance() + orders.getTotal() * 0.1f;  //10% hệ thống
        transactions2.setAmount(newBalance);
        manager.setBalance(newBalance);
        transactionsSet.add(transactions2);

        // Tạo transaction từ quản lý đến chủ sở hữu
        Transactions transactions3 = new Transactions();
        transactions3.setPayment(payment);
        transactions3.setStatus(TransactionEnum.SUCCESS);
        transactions3.setDescription("MANAGER TO OWNER");
        transactions3.setFrom(manager);
        User owner = orders.getOrderDetails().get(0).getKoi().getUser();
        transactions3.setTo(owner);
        float newShopBalance = owner.getBalance() + orders.getTotal() * (1 - 0.1f);
        transactions3.setAmount(newShopBalance);
        owner.setBalance(newShopBalance);
        transactionsSet.add(transactions3);

        // Lưu các transaction và cập nhật trạng thái của payment
        payment.setTransactions(transactionsSet);
        orders.setPayment(payment); // Liên kết payment với đơn hàng

        // Cập nhật trạng thái đơn hàng sau khi thanh toán thành công
        orders.setStatus(OrderStatusEnum.COMPLETED);  // Đặt trạng thái đơn hàng thành "COMPLETED"

        orderRepository.save(orders); // Lưu đơn hàng với payment đã cập nhật
        userRepository.save(manager);
        userRepository.save(owner);
        paymentRepository.save(payment);
    }


    // Cập nhật trạng thái đơn hàng, chỉ cho phép chuyển sang COMPLETED sau khi thanh toán hoàn tất
    public Orders updateOrderStatus(UUID orderId, OrderStatusEnum status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        // Kiểm tra nếu trạng thái là "PROCESSING" mới cho phép cập nhật
        if (order.getStatus() != OrderStatusEnum.PROCESSING) {
            throw new IllegalStateException("Order must be completed (payment successful) before changing status.");
        }

        // Nếu trạng thái yêu cầu là COMPLETED thì cập nhật
        if (status == OrderStatusEnum.COMPLETED) {
            order.setStatus(OrderStatusEnum.COMPLETED);
        } else {
            throw new IllegalStateException("Invalid status update request.");
        }

        return orderRepository.save(order);
    }



}