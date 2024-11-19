package com.example.koifarm.service;

import com.example.koifarm.entity.User;
import com.example.koifarm.exception.InvalidConsignmentTypeException;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsignmentService {
    @Autowired
    ConsignmentRepository consignmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    public Consignment createConsignment(ConsignmentRequest consignmentRequest) {
        User customer = authenticationService.getCurrentUser(); // Lấy người dùng hiện tại

        Consignment consignment = new Consignment();

        // Thiết lập các thuộc tính từ yêu cầu
        consignment.setKoiName(consignmentRequest.getKoiName());
        consignment.setBreed(consignmentRequest.getBreed());
        consignment.setSize(consignmentRequest.getSize());
        consignment.setAge(consignmentRequest.getAge());
        consignment.setGender(consignmentRequest.getGender());
        consignment.setExpectedPrice(consignmentRequest.getExpectedPrice());
        consignment.setQuantity(consignmentRequest.getQuantity());
        consignment.setCareDuration(consignmentRequest.getCareDuration());
        consignment.setCareFee(consignmentRequest.getCareFee());
        consignment.setSpecialRequirements(consignmentRequest.getSpecialRequirements());

        // Xử lý loại consignments
        try {
            consignment.setConsignmentType(ConsignmentType.valueOf(consignmentRequest.getConsignmentType().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidConsignmentTypeException("Loại consignments không hợp lệ: " + consignmentRequest.getConsignmentType());
        }

        consignment.setAddress(consignmentRequest.getAddress());
        consignment.setInspectionMethod(consignmentRequest.getInspectionMethod());
        consignment.setCreatedDate(LocalDateTime.now());
        consignment.setCustomer(customer); // Gán người dùng hiện tại vào consignments

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
}
