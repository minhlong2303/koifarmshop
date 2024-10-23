package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.ConsignmentDetails;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.repository.ConsignmentRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsignmentService {

    private static final Logger logger = LoggerFactory.getLogger(ConsignmentService.class);

    @Autowired
    private ConsignmentRepository consignmentRepository;

    @Transactional
    public void createConsignment(@Valid ConsignmentRequest consignmentRequest) {
        try {
            // Tạo đối tượng Consignment từ request
            Consignment consignment = new Consignment();
            consignment.setFullName(consignmentRequest.getFullName());
            consignment.setPhoneNumber(consignmentRequest.getPhoneNumber());
            consignment.setEmail(consignmentRequest.getEmail());
            consignment.setAddress(consignmentRequest.getAddress());
            consignment.setConsignmentDate(consignmentRequest.getConsignmentDate());
            consignment.setInspectionMethod(consignmentRequest.getInspectionMethod());
            consignment.setStatus("Pending");

            // Tạo danh sách chi tiết consignment từ request
            List<ConsignmentDetails> detailsList = consignmentRequest.getConsignmentDetails().stream().map(detailRequest -> {
                ConsignmentDetails details = new ConsignmentDetails();
                details.setQuantity(detailRequest.getQuantity());
                details.setKoiBreed(detailRequest.getBreed()); // Updated to match property name
                details.setSize(detailRequest.getSize()); // Assuming this is present in the request
                details.setHealthStatus(detailRequest.getHealthStatus());
                details.setGender(detailRequest.getGender());
                details.setAdditionalInfo(detailRequest.getAdditionalNotes()); // Updated to match property name
                details.setConsignment(consignment);
                return details;
            }).collect(Collectors.toList());

            consignment.setConsignmentDetails(detailsList);

            // Lưu consignment vào database
            consignmentRepository.save(consignment);
            logger.info("Consignment created successfully for: {}", consignmentRequest.getFullName());

        } catch (Exception e) {
            logger.error("Error creating consignment: {}", e.getMessage(), e);
            // Handle the exception as needed (e.g., throw a custom exception)
        }
    }
}
