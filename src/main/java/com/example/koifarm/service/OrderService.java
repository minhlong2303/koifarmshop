package com.example.koifarm.service;

import com.example.koifarm.entity.*;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.enums.PaymentEnums;
import com.example.koifarm.enums.Role;
import com.example.koifarm.enums.TransactionEnum;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.OrderDetailRequest;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.OrderRepository;
import com.example.koifarm.repository.PaymentRepository;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
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

    public Orders create(OrderRequest orderRequest){
        //lay thong tin nguoi vua tao order
        User customer = authenticationService.getCurrentUser();
        //create order
        Orders order = new Orders();
        List<OrderDetails> orderDetails = new ArrayList<>();
        float total = 0;

        order.setCustomer(customer);
        order.setDate(new Date());
        order.setStatus(OrderStatusEnum.PENDING);

        for(OrderDetailRequest orderDetailRequest: orderRequest.getDetail()){
            //lay id cua koi ma ng dung yeu cau mua
            Koi koi = koiRepository.findKoiByKoiID(orderDetailRequest.getKoiId());
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setPrice(koi.getPrice());
            orderDetail.setOrders(order);
            orderDetail.setKoi(koi);

            orderDetails.add(orderDetail);
            total += koi.getPrice() * orderDetailRequest.getQuantity();
        }

        order.setOrderDetails(orderDetails);
        order.setTotal(total);

        return orderRepository.save(order);
    }

    public List<Orders> get(){
        List<Orders> orders = orderRepository.findAll();
        return orders;
    }

    public String createUrl(OrderRequest orderRequest) throws  Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //tao orders
        Orders orders = create(orderRequest);
        //
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

    public void createTransaction(UUID uuid){
        //tim order
        Orders orders = orderRepository.findById(uuid)
                .orElseThrow(()->new EntityNotFoundException("Order not found!"));

        // Check if a payment already exists for this order
        if (orders.getPayment() != null) {
            throw new RuntimeException("Payment already exists for this order.");
        }

        //tao payment
        Payment payment = new Payment();
        payment.setOrders(orders);
        payment.setCreateAt(new Date());
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

        // Lấy trạng thái hiện tại của đơn hàng
        OrderStatusEnum currentStatus = order.getStatus();

        // Kiểm tra nếu chuyển trạng thái không hợp lệ
        if (isStatusChangeInvalid(currentStatus, status)) {
            throw new IllegalStateException("Chuyển trạng thái không hợp lệ");
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }

    // Kiểm tra chuyển trạng thái hợp lệ
    private boolean isStatusChangeInvalid(OrderStatusEnum currentStatus, OrderStatusEnum newStatus) {
        switch (currentStatus) {
            case PENDING:
                // Chỉ có thể chuyển từ PENDING sang PROCESSING hoặc CANCELLED
                return newStatus != OrderStatusEnum.PROCESSING && newStatus != OrderStatusEnum.CANCELLED;

            case PROCESSING:
                // Chỉ có thể chuyển từ PROCESSING sang COMPLETED hoặc CANCELLED
                return newStatus != OrderStatusEnum.COMPLETED && newStatus != OrderStatusEnum.CANCELLED;

            case COMPLETED:
            case CANCELLED:
                // Không thể chuyển trạng thái nếu đã hoàn thành hoặc đã hủy
                return true;

            default:
                return false;
        }
    }

    //Huy don hang
    public Orders cancelOrder(UUID orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        // Kiểm tra nếu trạng thái đơn hàng là PENDING hoặc PROCESSING thì mới có thể hủy
        if (order.getStatus() == OrderStatusEnum.COMPLETED || order.getStatus() == OrderStatusEnum.CANCELLED) {
            throw new IllegalStateException("Không thể hủy đơn hàng đã hoàn thành hoặc đã bị hủy.");
        }

        // Cập nhật trạng thái đơn hàng thành CANCELLED
        order.setStatus(OrderStatusEnum.CANCELLED);
        return orderRepository.save(order);
    }

    //Get orders
    public Orders getOrderById(UUID orderId){
        Orders oldOrders = orderRepository.findOrdersById(orderId);
        if (oldOrders == null) throw new EntityNotFoundException("Orders not found!");
        return oldOrders;
    }

    //delete order
    public Orders deleteOrder(UUID orderId){
        Orders oldOrders = orderRepository.findOrdersById(orderId);
        if (oldOrders == null) throw new EntityNotFoundException("Orders not found!");
        oldOrders.setDeleted(true);
        return orderRepository.save(oldOrders);
    }


}
