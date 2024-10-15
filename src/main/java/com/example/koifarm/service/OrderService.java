package com.example.koifarm.service;

import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.OrderDetails;
import com.example.koifarm.entity.Orders;
import com.example.koifarm.entity.User;
import com.example.koifarm.model.OrderDetailRequest;
import com.example.koifarm.model.OrderRequest;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    OrderRepository orderRepository;

    public Orders create(OrderRequest orderRequest){
        //lay thong tin nguoi vua tao order
        User customer = authenticationService.getCurrentUser();
        Orders order = new Orders();
        List<OrderDetails> orderDetails = new ArrayList<>();
        float total = 0;

        order.setCustomer(customer);
        order.setDate(new Date());

        for(OrderDetailRequest orderDetailRequest: orderRequest.getDetail()){
            //lay id cua koi ma ng dung yeu cau mua
            Koi koi = koiRepository.findKoiByKoiID(orderDetailRequest.getKoiId());
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setPrice(orderDetail.getPrice());
            orderDetail.setOrders(order);
            orderDetail.setKoi(koi);

            orderDetails.add(orderDetail);
            total += koi.getPrice() * orderDetailRequest.getQuantity();
        }

        order.setOrderDetails(orderDetails);
        order.setTotal(total);

        return orderRepository.save(order);

    }
}
