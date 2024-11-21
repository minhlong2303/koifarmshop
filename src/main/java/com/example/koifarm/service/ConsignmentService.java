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
    KoiService koiService;
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
        User customer = authenticationService.getCurrentUser(); // Lấy người dùng hiện tại

        Consignment consignment = new Consignment();

        // Thiết lập thông tin khách hàng
        consignment.setFirstName(consignmentRequest.getFirstName());
        consignment.setLastName(consignmentRequest.getLastName());
        consignment.setPhone(consignmentRequest.getPhone());
        consignment.setEmail(consignmentRequest.getEmail());
        consignment.setAddress(consignmentRequest.getAddress());

        // Thiết lập thông tin cá Koi
        consignment.setKoiName(consignmentRequest.getKoiName());
        consignment.setBreed(consignmentRequest.getBreed());
        consignment.setSize(consignmentRequest.getSize());
        consignment.setAge(consignmentRequest.getAge());
        consignment.setGender(consignmentRequest.getGender());
        consignment.setExpectedPrice(consignmentRequest.getExpectedPrice());

        // Thiết lập các thông tin ký gửi
        try {
            consignment.setConsignmentType(consignmentRequest.getConsignmentType());
        } catch (IllegalArgumentException e) {
            throw new InvalidConsignmentTypeException("Loại consignments không hợp lệ: " + consignmentRequest.getConsignmentType());
        }

        consignment.setInspectionMethod(consignmentRequest.getInspectionMethod());

        // Thiết lập ảnh
        consignment.setKoiImageUrl(consignmentRequest.getKoiImageUrl());
        consignment.setCertificateImageUrl(consignmentRequest.getCertificateImageUrl());

        // Thông tin khác
        consignment.setCreatedDate(LocalDateTime.now());
        consignment.setCustomer(customer); // Gán người dùng hiện tại vào consignments

        // Lưu thông tin consignments vào cơ sở dữ liệu
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

    public void deleteConsignmentById(UUID consignmentId) {
        if (!consignmentRepository.existsById(consignmentId)) {
            throw new EntityNotFoundException("Consignment with ID " + consignmentId + " not found");
        }
        consignmentRepository.deleteById(consignmentId);
    }


}
