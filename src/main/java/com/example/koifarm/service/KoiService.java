package com.example.koifarm.service;

import com.example.koifarm.entity.Consignment;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.entity.User;
import com.example.koifarm.enums.ConsignmentStatusEnum;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.KoiSpeciesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KoiService {
    @Autowired
    KoiRepository koiRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    KoiSpeciesRepository koiSpeciesRepository;
    //create
    public Koi create(KoiRequest koiRequest){
        Koi koi = modelMapper.map(koiRequest, Koi.class);

        //luu thong tin nguoi tao
        User user = authenticationService.getCurrentUser();
        koi.setUser(user);

        KoiSpecies koiSpecies = koiSpeciesRepository.findKoiSpeciesById(koiRequest.getSpeciesId());
        if (koiSpecies == null) {
            throw new EntityNotFoundException("Koi Species not found!");
        }
        koi.setSpecies(koiSpecies);

        Koi newKoi = koiRepository.save(koi);
        return newKoi;
    }

    public Koi createFromConsignment(Consignment consignment) {
        if (consignment == null) {
            throw new IllegalArgumentException("Consignment cannot be null");
        }

        // Kiểm tra trạng thái consignment phải là COMPLETED
        if (!consignment.getStatus().equals(ConsignmentStatusEnum.COMPLETED)) {
            throw new IllegalStateException("Consignment must be in COMPLETED status to create Koi");
        }

        Koi koi = new Koi();

        // Lấy thông tin từ consignment
        koi.setName(consignment.getKoiName());
        koi.setBreed(consignment.getBreed());
        koi.setSize(Float.parseFloat(consignment.getSize()));
        koi.setGender(consignment.getGender());
        koi.setImage(consignment.getKoiImageUrl());
        koi.setStatus("available");

        // Gắn người tạo từ phiên đăng nhập hiện tại
        User user = consignment.getCustomer();
        koi.setUser(user);

        // Lưu Koi vào database
        return koiRepository.save(koi);
    }



    //read
    public List<Koi> getAllKoi(){
        List<Koi> kois = koiRepository.findKoiByIsDeletedFalse();
        return  kois;
    }


    //Update
    public Koi update(UUID koiID, Koi koi){
        //b1: tim Koi can duoc update
        Koi oldKoi = getKoiById(koiID);

        //b2: cap nhat thong tin cua no
        oldKoi.setName(koi.getName());
        oldKoi.setOrigin(koi.getOrigin());
        oldKoi.setGender(koi.getGender());
        oldKoi.setSize(koi.getSize());
        oldKoi.setPrice(koi.getPrice());
        oldKoi.setBreed(koi.getBreed());
        oldKoi.setLocation(koi.getLocation());
        oldKoi.setOwner(koi.getOwner());
        oldKoi.setDescription(koi.getDescription());
        oldKoi.setImage(koi.getImage());
        //b3: luu xuong DB
        return koiRepository.save(oldKoi);
    }

    //Delete
    public Koi delete(UUID koiID){
        Koi oldKoi = getKoiById(koiID);
        oldKoi.setDeleted(true);
        return koiRepository.save(oldKoi);
    }

    public Koi getKoiById(UUID koiID) {
        return koiRepository.findKoiByKoiID(koiID)
                .orElseThrow(() -> new EntityNotFoundException("Koi not found!"));
    }


}
