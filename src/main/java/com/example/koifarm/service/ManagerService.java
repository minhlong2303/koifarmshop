package com.example.koifarm.service;

import com.example.koifarm.entity.User;
import com.example.koifarm.enums.Role;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.TransactionRepository;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ManagerService {
    @Autowired
    KoiRepository koiRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AuthenticationService authenticationService;

    public Map<String, Object> getDashboardStats(){
        Map<String, Object> stats = new HashMap<>();
        //dem so san pham trong he thong
        long totalProducts = koiRepository.count();
        stats.put("totalProducts", totalProducts);

        //so luong customer
        long customerCount = userRepository.countByRole(Role.CUSTOMER);
        stats.put("customerCount", customerCount);

        //so luong owner
        long ownerCount = userRepository.countByRole(Role.OWNER);
        stats.put("ownerCount", ownerCount);


        //top 5 san pham ban chay nhat
        List<Object[]> topKois = koiRepository.findTop5BestSellerProducts();
        List<Map<String, Object>> topKoisList = new ArrayList<>();

        for(Object[] koiData: topKois){
            Map<String, Object> koiInfo = new HashMap<>();
            koiInfo.put("koiName", koiData[0]);
            koiInfo.put("totalSold", koiData[1]);
            topKoisList.add(koiInfo);
        }
        stats.put("topKoi", topKoisList);
        return stats;
    }

    public Map<String, Object> getMonthlyRevenue(){
        Map<String, Object> revenueDate = new HashMap<>();
        User user = authenticationService.getCurrentUser();
        if(user==null){
            throw new RuntimeException("You need to login first");
        }

        List<Object[]> monthlyRevenue = transactionRepository.calculateMonthlyRev(user.getId());
        List<Map<String, Object>> monthlyRevenueList = new ArrayList<>();

        for(Object[] result: monthlyRevenue){
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("year", result[0]);
            monthData.put("month", result[1]);
            monthData.put("totalRevenue", result[2]);
            monthlyRevenueList.add(monthData);
        }
        revenueDate.put("monthlyRevenue", monthlyRevenueList);
        return revenueDate;
    }

}
