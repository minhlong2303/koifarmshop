package com.example.koifarm.service;


import com.example.koifarm.entity.*;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.enums.PaymentEnums;
import com.example.koifarm.enums.Role;
import com.example.koifarm.enums.TransactionEnum;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.BatchKoiOrderDetailRequest;
import com.example.koifarm.model.BatchKoiOrderRequest;
import com.example.koifarm.repository.BatchKoiOrderRepository;
import com.example.koifarm.repository.BatchKoiRepository;
import com.example.koifarm.repository.PaymentRepository;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class BatchKoiOrderService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BatchKoiRepository batchKoiRepository;

    @Autowired
    BatchKoiOrderRepository batchKoiOrderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    public BatchKoiOrder createOrder(BatchKoiOrderRequest batchKoiOrderRequest){
        //lay thong tin nguoi vua tao order
        User customer = authenticationService.getCurrentUser();
        //create order
        BatchKoiOrder batchKoiOrder = new BatchKoiOrder();
        List<BatchKoiOrderDetail> batchKoiOrderDetail = new ArrayList<>();
        float total = 0;

        batchKoiOrder.setCustomer(customer);
        batchKoiOrder.setDate(new Date());
        batchKoiOrder.setStatus(OrderStatusEnum.PENDING);

        for(BatchKoiOrderDetailRequest batchKoiOrderDetailRequest: batchKoiOrderRequest.getDetail()){
            //lay id cua koi ma ng dung yeu cau mua
            BatchKoi batchKoi = batchKoiRepository.findBatchKoiBybatchKoiID(batchKoiOrderDetailRequest.getBatchKoiId());
            BatchKoiOrderDetail batchKoiOrderDetails = new BatchKoiOrderDetail();
            batchKoiOrderDetails.setQuantity(batchKoiOrderDetailRequest.getQuantity());
            batchKoiOrderDetails.setPrice(batchKoi.getPrice());
            batchKoiOrderDetails.setBatchKoiOrder(batchKoiOrder);
            batchKoiOrderDetails.setBatchKoi(batchKoi);

            batchKoiOrderDetail.add(batchKoiOrderDetails);
            total += batchKoi.getPrice() * batchKoiOrderDetailRequest.getQuantity();
        }

        batchKoiOrder.setBatchOrderDetails(batchKoiOrderDetail);
        batchKoiOrder.setTotal(total);

        return batchKoiOrderRepository.save(batchKoiOrder);

    }

    public List<BatchKoiOrder> get(){
        User user = authenticationService.getCurrentUser();
        List<BatchKoiOrder> batchKoiOrders = batchKoiOrderRepository.findBatchKoiOrderByCustomer(user);
        return batchKoiOrders;
    }

    public String createUrl(BatchKoiOrderRequest batchKoiOrderRequest) throws  Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //tao orders
        BatchKoiOrder batchKoiOrder = createOrder(batchKoiOrderRequest);
        //
        float money = batchKoiOrder.getTotal()*100;
        String amount = String.valueOf((int) money);

        String tmnCode = "OHNZVMJ7";
        String secretKey = "KL9DNZY17C0XVP5QQI95KJJX49F2U71E";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://localhost:5173/success?orderID=" + batchKoiOrder.getId(); //return trang thanh toan thanh cong
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", batchKoiOrder.getId().toString()); //moi don hang la duy nhat
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + batchKoiOrder.getId());
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
        BatchKoiOrder batchKoiOrder = batchKoiOrderRepository.findById(uuid)
                .orElseThrow(()->new EntityNotFoundException("Order not found!"));

        // Check if a payment already exists for this order
        if (batchKoiOrder.getPayment() != null) {
            throw new RuntimeException("Payment already exists for this order.");
        }

        //tao payment
        Payment payment = new Payment();
        payment.setBatchKoiOrder(batchKoiOrder);
        payment.setCreateAt(new Date());
        payment.setPayment_method(PaymentEnums.BANKING);
        payment.setTotal(batchKoiOrder.getTotal());

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
        float newBalance = manager.getBalance() + batchKoiOrder.getTotal()*0.1f;  //10% cua he thong
        transactions2.setAmount(newBalance);
        manager.setBalance(newBalance);
        transactionsSet.add(transactions2); //add to transacrionSet

        //MANAGER TO OWNER
        Transactions transactions3 = new Transactions();
        transactions3.setPayment(payment);
        transactions3.setStatus(TransactionEnum.SUCCESS);
        transactions3.setDescription("MANAGER TO OWNER");
        transactions3.setFrom(manager);
        User owner = batchKoiOrder.getBatchOrderDetails().get(0).getBatchKoi().getUser();
        transactions3.setTo(owner);
        float newShopBalance = owner.getBalance()+ batchKoiOrder.getTotal()*(1-0.1f);
        transactions3.setAmount(newShopBalance);
        owner.setBalance(newShopBalance);
        transactionsSet.add(transactions3);

        // Save payment and transactions
        payment.setTransactions(transactionsSet);

        batchKoiOrder.setPayment(payment); // Set the payment for the order
        batchKoiOrderRepository.save(batchKoiOrder); // Save the order with the payment

        userRepository.save(manager);
        userRepository.save(owner);
        paymentRepository.save(payment);

    }

    //Update Status
    public BatchKoiOrder updateOrderStatus(UUID orderId, OrderStatusEnum status) {
        BatchKoiOrder batchKoiOrder = batchKoiOrderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        batchKoiOrder.setStatus(status);
        return batchKoiOrderRepository.save(batchKoiOrder);
    }


}