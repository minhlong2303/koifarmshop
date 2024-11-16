package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.User;
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
    private ConsignmentRepository consignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KoiService koiService;  // Inject KoiService để tạo cá Koi

    // Hàm duyệt consignment và tạo Koi trong cửa hàng
    public Consignment approveConsignment(UUID consignmentID) {
        // Lấy đơn ký gửi từ DB
        Consignment consignment = consignmentRepository.findById(consignmentID)
                .orElseThrow(() -> new RuntimeException("Consignment not found"));

        // Kiểm tra trạng thái, chỉ duyệt nếu trạng thái là PROCESSING
        if (consignment.getStatus() != Consignment.ConsignmentStatus.PROCESSING) {
            throw new RuntimeException("Consignment is not in a PROCESSING state");
        }

        // Cập nhật trạng thái của consignment
        consignment.setStatus(Consignment.ConsignmentStatus.APPROVED);
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
        // Gán các thông tin khác nếu cần thiết
        Koi newKoi = koiService.create(koiRequest);  // Gọi phương thức create để tạo Koi

        // Cập nhật chủ sở hữu của cá Koi trong hệ thống
        newKoi.setOwner(String.valueOf(seller.getId()));
        newKoi.setStatus("available");  // Trạng thái của cá Koi khi đã được tạo trong cửa hàng

        // Lưu thông tin cá Koi vào hệ thống
        koiService.create(koiRequest);

        // Lưu lại consignment đã duyệt
        Consignment updatedConsignment = consignmentRepository.save(consignment);

        return updatedConsignment;
    }

    // Tạo đơn ký gửi mới
    public Consignment createConsignment(ConsignmentRequest consignmentRequest) {
        // Tạo consignment mới với thông tin yêu cầu ký gửi
        Consignment consignment = new Consignment();
        consignment.setKoi(consignmentRequest.getKoi());
        consignment.setUser(consignmentRequest.getSeller());
        consignment.setStatus(Consignment.ConsignmentStatus.PROCESSING);  // Trạng thái mới là PROCESSING

        // Lưu consignment mới vào cơ sở dữ liệu
        return consignmentRepository.save(consignment);
    }

    // Lấy tất cả đơn ký gửi
    public List<Consignment> getAllConsignments() {
        return consignmentRepository.findAll();
    }
}
