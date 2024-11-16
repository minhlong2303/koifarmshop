package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.User;
import com.example.koifarm.model.ConsignmentRequest;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.repository.ConsignmentRepository;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConsignmentService {

    @Autowired
    private ConsignmentRepository consignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KoiService koiService;  // Inject KoiService để tạo cá Koi

    @Autowired
    private KoiRepository koiRepository;

    public Consignment approveConsignment(UUID consignmentID) {
        // Lấy đơn ký gửi từ DB
        Consignment consignment = consignmentRepository.findById(consignmentID)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));

        // Kiểm tra trạng thái, chỉ duyệt nếu trạng thái là PROCESSING
        if (consignment.getConsignmentStatus() != Consignment.ConsignmentStatus.PROCESSING) {
            throw new RuntimeException("Consignment is not in a PROCESSING state");
        }

        // Cập nhật trạng thái của consignment
        consignment.setConsignmentStatus(Consignment.ConsignmentStatus.APPROVED);
        consignment.setApprovedAt(LocalDateTime.now());

        // Lấy thông tin cá Koi và người bán
        Koi koi = consignment.getKoi();
        User seller = consignment.getUser();  // Lấy thông tin người bán

        if (seller == null) {
            throw new RuntimeException("Seller information is missing");
        }

        // Tạo Koi mới trong cửa hàng
        KoiRequest koiRequest = new KoiRequest();
        koiRequest.setSpeciesId(koi.getSpecies().getId());  // Gán ID giống cá từ đơn ký gửi
        koiRequest.setName(koi.getName());
        koiRequest.setOrigin(koi.getOrigin());
        koiRequest.setGender(koi.getGender());
        koiRequest.setSize(koi.getSize());
        koiRequest.setPrice(koi.getPrice());
        koiRequest.setBreed(koi.getBreed());
        koiRequest.setDescription(koi.getDescription());
        koiRequest.setImage(koi.getImage());

        Koi newKoi = koiService.create(koiRequest);  // Tạo cá Koi mới

        // Cập nhật chủ sở hữu của cá Koi trong hệ thống
        newKoi.setOwner(String.valueOf(seller.getId()));
        newKoi.setStatus("available");  // Trạng thái của cá Koi khi đã được tạo trong cửa hàng

        // Lưu cá Koi mới vào cơ sở dữ liệu
        koiRepository.save(newKoi);  // Lưu Koi vào hệ thống

        // Cập nhật lại consignment với Koi mới, nếu cần
        consignment.setKoi(newKoi);  // Ensure Koi is associated with the Consignment

        // Lưu lại consignment đã duyệt
        consignmentRepository.save(consignment);

        return consignment;  // Trả về đơn ký gửi đã duyệt
    }


    public Consignment createConsignment(ConsignmentRequest consignmentRequest) {
        // Chuyển đổi từ ConsignmentRequest sang KoiRequest
        KoiRequest koiRequest = new KoiRequest();
        koiRequest.setName(consignmentRequest.getName());
        koiRequest.setOrigin(consignmentRequest.getOrigin());
        koiRequest.setGender(consignmentRequest.getGender());
        koiRequest.setSize(consignmentRequest.getSize());
        koiRequest.setPrice(consignmentRequest.getPrice());
        koiRequest.setBreed(consignmentRequest.getBreed());
        koiRequest.setDescription(consignmentRequest.getDescription());
        koiRequest.setImage(consignmentRequest.getImage());

        // Gọi phương thức create của KoiService để tạo cá Koi mới
        Koi newKoi = koiService.create(koiRequest);

        // Tạo Consignment mới từ Koi
        Consignment consignment = new Consignment();
        consignment.setKoi(newKoi);
        consignment.setConsignmentStatus(Consignment.ConsignmentStatus.PROCESSING); // Example: set a status
        consignment.setUser(userRepository.findUserById(consignment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found")));  // Assuming userId is part of ConsignmentRequest
        consignment.setCreatedAt(LocalDateTime.now());

        // Lưu Consignment vào cơ sở dữ liệu
        consignmentRepository.save(consignment);

        return consignment;  // Trả về Consignment
    }


    public Consignment getConsignmentById(UUID consignmentId) {
        return consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));
    }

    public Consignment updateConsignment(UUID consignmentId, ConsignmentRequest consignmentRequest) {
        // Tìm đơn ký gửi hiện tại
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));

        // Cập nhật thông tin cá Koi trong đơn ký gửi
        consignment.getKoi().setName(consignmentRequest.getName());
        consignment.getKoi().setOrigin(consignmentRequest.getOrigin());
        consignment.getKoi().setGender(consignmentRequest.getGender());
        consignment.getKoi().setSize(consignmentRequest.getSize());
        consignment.getKoi().setPrice(consignmentRequest.getPrice());
        consignment.getKoi().setBreed(consignmentRequest.getBreed());
        consignment.getKoi().setDescription(consignmentRequest.getDescription());
        consignment.getKoi().setImage(consignmentRequest.getImage());

        // Lưu lại đơn ký gửi đã cập nhật
        return consignmentRepository.save(consignment);
    }

    public void deleteConsignment(UUID consignmentId) {
        Consignment consignment = consignmentRepository.findById(consignmentId)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));

        // Xóa cá Koi nếu cần (nếu có yêu cầu)
        if (consignment.getKoi() != null) {
            koiRepository.delete(consignment.getKoi());
        }

        // Xóa đơn ký gửi
        consignmentRepository.delete(consignment);
    }


}

