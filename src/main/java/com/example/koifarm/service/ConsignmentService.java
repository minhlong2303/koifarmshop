package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.ConsignmentDetails;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.User;
import com.example.koifarm.model.ConsignmentDetailRequest;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.repository.ConsignmentRepository;
import com.example.koifarm.repository.KoiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConsignmentService {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    KoiRepository koiRepository;

    @Autowired
    ConsignmentRepository consignmentRepository;

    public Consignment create(ConsignmentRequest consignmentRequest) {
        // lấy thông tin của người dùng tạo đơn ký gửi
        User customer = authenticationService.getCurrentUser();
        Consignment consignment = new Consignment();
        List<ConsignmentDetails> consignmentDetails = new ArrayList<>();
        float total = 0;

        consignment.setCustomer(customer);
        consignment.setConsignmentDate(new Date());

        for(ConsignmentDetailRequest consignmentDetailRequest: consignmentRequest.getDetail()){
            //lấy id của koi mà người dùng yêu cầu bán
            Koi koi = koiRepository.findKoiByKoiID(consignmentDetailRequest.getKoiId());
            ConsignmentDetails consignmentDetail = new ConsignmentDetails();
            consignmentDetail.setQuantity(consignmentDetailRequest.getQuantity());
            consignmentDetail.setPrice(consignmentDetail.getPrice());
            consignmentDetail.setConsignment(consignment);
            consignmentDetail.setKoi(koi);

            consignmentDetails.add(consignmentDetail);
            total += koi.getPrice() * consignmentDetailRequest.getQuantity();
        }
        consignment.setConsignmentDetails(consignmentDetails);
        consignment.setTotal(total);

        return consignmentRepository.save(consignment);
    }
}
