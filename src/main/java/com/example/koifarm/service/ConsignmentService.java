package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.User;
import com.example.koifarm.enums.ConsignmentStatus;
import com.example.koifarm.enums.ConsignmentStatusEnum;
import com.example.koifarm.enums.ConsignmentType;
import com.example.koifarm.enums.OrderStatusEnum;
import com.example.koifarm.exception.InvalidConsignmentTypeException;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.repository.ConsignmentRepository;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ConsignmentService {

    @Autowired
    ConsignmentRepository consignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    KoiService koiService;

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

        // Thiết lập các thông tin ký gửi
        try {
            consignment.setConsignmentType(ConsignmentType.valueOf(consignmentRequest.getConsignmentType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidConsignmentTypeException("Loại consignments không hợp lệ: " + consignmentRequest.getConsignmentType());
        }



        // Thiết lập ảnh
        consignment.setKoiImageUrl(consignmentRequest.getKoiImageUrl());


        // Thông tin khác
        consignment.setCreatedDate(LocalDateTime.now());
        consignment.setCustomer(customer); // Gán người dùng hiện tại vào consignments
        consignment.setConsignmentStatus(ConsignmentStatus.PENDING); // Gán trạng thái ban đầu là PROCESSING

        // Lưu thông tin consignments vào cơ sở dữ liệu
        return consignmentRepository.save(consignment);
    }

    public List<Consignment> getAllConsignments() {
        return consignmentRepository.findAll();
    }

    public List<Consignment> getConsignmentsByType(ConsignmentType type) {
        return consignmentRepository.findByConsignmentType(type);
    }

    public List<Consignment> getConsignmentsByUserId(Long userId) {
        return consignmentRepository.findByCustomer_Id(userId);
    }

    public List<Consignment> getConsignmentsByKoiName(String koiName) {
        return consignmentRepository.findByKoiNameContainingIgnoreCase(koiName);
    }

    // Phương thức mới để lấy consignments của người dùng hiện tại
    public List<Consignment> getConsignmentsByCurrentUser() {
        User customer = authenticationService.getCurrentUser(); // Lấy người dùng hiện tại
        return consignmentRepository.findByCustomer_Id(customer.getId()); // Truy vấn consignments theo ID người dùng
    }

    public Consignment approveConsignment(Long consignmentId) {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Consignment not found"));

        consignment.setConsignmentStatus(ConsignmentStatus.APPROVED);
        return consignmentRepository.save(consignment);
    }

    public void processKoiCreation(UUID id) {
        // Tìm consignment theo ID
        Consignment consignment = consignmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consignment not found!"));

        // Kiểm tra trạng thái consignment
        if (consignment.getConsignmentStatus() != ConsignmentStatus.PENDING) {
            throw new IllegalStateException("Consignment is not in a valid state for approval.");
        }


        // Tạo mới Koi
        KoiRequest koiRequest = mapConsignmentToKoiRequest(consignment);
        koiService.create(koiRequest);

        // Cập nhật trạng thái consignment
        consignment.setConsignmentStatus(ConsignmentStatus.APPROVED);
        consignmentRepository.save(consignment);
    }
    private KoiRequest mapConsignmentToKoiRequest(Consignment consignment) {
        String sizeStr = consignment.getSize(); // Get the size as a string
        Float size = null;

        // Check if the size string is a valid number
        try {
            size = Float.parseFloat(sizeStr);  // Attempt to parse it as a float
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid size value: " + sizeStr); // Invalid number format
        }

        return KoiRequest.builder()
                .name(consignment.getKoiName())
                .breed(consignment.getBreed())
                .size(size)  // Valid size after conversion
                .gender(consignment.getGender())
                .image(consignment.getKoiImageUrl())
                .build();
    }





}
