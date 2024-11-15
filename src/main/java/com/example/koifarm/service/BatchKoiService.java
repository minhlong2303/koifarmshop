package com.example.koifarm.service;

import com.example.koifarm.entity.BatchKoi;
import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.entity.User;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.BatchKoiRequest;
import com.example.koifarm.model.BatchKoiResponse;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.repository.BatchKoiRepository;
import com.example.koifarm.repository.KoiSpeciesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BatchKoiService {

    @Autowired
    KoiSpeciesRepository koiSpeciesRepository;

    @Autowired
    BatchKoiRepository batchKoiRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    //create
    public BatchKoi create(BatchKoiRequest batchKoiRequest){
        BatchKoi batchKoi = modelMapper.map(batchKoiRequest, BatchKoi.class);

        //luu thong tin nguoi tao
        User user = authenticationService.getCurrentUser();
        batchKoi.setUser(user);

        KoiSpecies koiSpecies = koiSpeciesRepository.findKoiSpeciesById(batchKoiRequest.getSpeciesId());
        if (koiSpecies == null) {
            throw new jakarta.persistence.EntityNotFoundException("Koi Species not found!");
        }
        batchKoi.setSpecies(koiSpecies);

        BatchKoi newBatchKoi = batchKoiRepository.save(batchKoi);
        return newBatchKoi;
    }


    public List<BatchKoi> getAllBatchKoi(){
        List<BatchKoi> batchKoiList = batchKoiRepository.findBatchKoiByIsDeletedFalse();
        return batchKoiList;
    }

    //get
    public BatchKoi getBatchKoiById(UUID batchKoiID){
        BatchKoi oldBatchKoi = batchKoiRepository.findBatchKoiByBatchKoiID(batchKoiID);
        if (oldBatchKoi == null){
            throw new EntityNotFoundException("BatchKoi not found!");
        }
        return oldBatchKoi;
    }

    //update
    public BatchKoi update(UUID batchKoiID, BatchKoi batchKoi){
        BatchKoi oldBatchKoi = getBatchKoiById(batchKoiID);

        oldBatchKoi.setName(batchKoi.getName());
        oldBatchKoi.setPrice(batchKoi.getPrice());
        oldBatchKoi.setQuantity(batchKoi.getQuantity());
        oldBatchKoi.setDescription(batchKoi.getDescription());

        return batchKoiRepository.save(oldBatchKoi);
    }

    //delete
    public BatchKoi delete(UUID batchKoiID){
        BatchKoi oldBatchKoi = getBatchKoiById(batchKoiID);
        oldBatchKoi.setDeleted(true);
        return batchKoiRepository.save(oldBatchKoi);
    }


}