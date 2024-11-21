// File: com/example/koifarm/service/ConsignmentService.java
package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.User;
import com.example.koifarm.enums.ConsignmentType;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.exception.InvalidConsignmentTypeException;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.repository.ConsignmentRepository;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ConsignmentService {

    @Autowired ConsignmentRepository consignmentRepository;
    @Autowired UserRepository userRepository;
    @Autowired AuthenticationService authenticationService;
    @Autowired KoiRepository koiRepository;
    @Autowired
    private KoiService koiService;

    public ConsignmentService(ConsignmentRepository consignmentRepository,
                              UserRepository userRepository,
                              AuthenticationService authenticationService,
                              KoiRepository koiRepository) {
        this.consignmentRepository = consignmentRepository;
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.koiRepository = koiRepository;
    }

    public Consignment createConsignment(ConsignmentRequest consignmentRequest) {
        log.info("Creating consignment for request: {}", consignmentRequest);

        // Lấy thông tin người dùng hiện tại
        User customer = authenticationService.getCurrentUser();
        if (customer == null) {
            throw new EntityNotFoundException("Current user not found.");
        }

        Consignment consignment = new Consignment();
        populateConsignmentDetails(consignment, consignmentRequest, customer);

        log.info("Consignment created successfully for user: {}", customer.getId());
        return consignmentRepository.save(consignment);
    }

    public Koi createKoiFromConsignment(UUID consignmentId) {
        // Lấy thông tin Consignment từ database
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Consignment not found"));

        // Chuyển đổi thông tin từ Consignment sang KoiRequest
        KoiRequest koiRequest = new KoiRequest();
        koiRequest.setName(consignment.getKoiName());
        koiRequest.setOrigin("Nhật Bản");
        koiRequest.setGender(consignment.getGender());
        koiRequest.setSize(BigDecimal.valueOf(consignment.getSize()));
        koiRequest.setPrice(consignment.getExpectedPrice());
        koiRequest.setBreed(consignment.getBreed());
        koiRequest.setLocation("Koi Farm");
        koiRequest.setOwner(consignment.getLastName());
        koiRequest.setDescription("Koi kí gửi");
        koiRequest.setSpeciesId(1); // Đặt mặc định là 1

        // Tạo mới Koi
        Koi newKoi = koiService.create(koiRequest);

        // Cập nhật trạng thái Consignment thành "approved"
        consignment.setStatus("APPROVED");
        consignmentRepository.save(consignment);

        return newKoi;
    }

    public List<Consignment> getAllConsignments() {
        log.info("Fetching all consignments");
        return consignmentRepository.findAll();
    }

    public List<Consignment> getConsignmentsByType(ConsignmentType type) {
        log.info("Fetching consignments by type: {}", type);
        return consignmentRepository.findByConsignmentType(type);
    }

    public List<Consignment> getConsignmentsByUserId(Long userId) {
        log.info("Fetching consignments for user ID: {}", userId);
        return consignmentRepository.findByCustomer_Id(userId);
    }

    public List<Consignment> getConsignmentsByKoiName(String koiName) {
        log.info("Fetching consignments with Koi name containing: {}", koiName);
        return consignmentRepository.findByKoiNameContainingIgnoreCase(koiName);
    }

    public List<Consignment> getConsignmentsByCurrentUser() {
        log.info("Fetching consignments for current user");
        User customer = authenticationService.getCurrentUser();
        if (customer == null) {
            throw new EntityNotFoundException("Current user not found.");
        }
        return consignmentRepository.findByCustomer_Id(customer.getId());
    }

    // Helper method to populate consignment details
    private void populateConsignmentDetails(Consignment consignment, ConsignmentRequest request, User customer) {
        consignment.setFirstName(request.getFirstName());
        consignment.setLastName(request.getLastName());
        consignment.setPhone(request.getPhone());
        consignment.setEmail(request.getEmail());
        consignment.setAddress(request.getAddress());

        consignment.setKoiName(request.getKoiName());
        consignment.setBreed(request.getBreed());
        consignment.setSize(request.getSize());
        consignment.setAge(request.getAge());
        consignment.setGender(request.getGender());

        try {
            consignment.setConsignmentType(ConsignmentType.valueOf(request.getConsignmentType().toString().toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.error("Invalid consignment type: {}", request.getConsignmentType());
            throw new InvalidConsignmentTypeException("Invalid consignment type: " + request.getConsignmentType());
        }

        consignment.setInspectionMethod(request.getInspectionMethod());
        consignment.setExpectedPrice(request.getExpectedPrice());
        consignment.setKoiImageUrl(request.getKoiImageUrl());
        consignment.setCertificateImageUrl(request.getCertificateImageUrl());

        consignment.setCreatedDate(LocalDateTime.now());
        consignment.setCustomer(customer);
    }

    // Helper method to create a Koi object from a consignment
    private Koi createKoiFromConsignment(Consignment consignment) {
        Koi koi = new Koi();
        koi.setName(consignment.getKoiName());
        koi.setBreed(consignment.getBreed());
        koi.setSize(consignment.getSize());
        koi.setPrice(consignment.getExpectedPrice());
        koi.setGender(consignment.getGender());
        koi.setImage(consignment.getKoiImageUrl());
        koi.setDescription("Age: " + consignment.getAge());
        koi.setStatus("available");
        koi.setAvailable(true);
        koi.setConsignment(consignment);
        return koi;
    }
}
