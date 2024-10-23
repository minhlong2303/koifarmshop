package com.example.koifarm.service;

import com.example.koifarm.entity.BatchKoi;
import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.BatchKoiRequest;
import com.example.koifarm.model.BatchKoiResponse;
import com.example.koifarm.repository.BatchKoiRepository;
import com.example.koifarm.repository.KoiSpeciesRepository;
import org.hibernate.query.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BatchKoiService {

    @Autowired
    KoiSpeciesRepository koiSpeciesRepository;

    @Autowired
    BatchKoiRepository batchKoiRepository;

    //create
    public BatchKoi create(BatchKoiRequest batchKoiRequest){
        BatchKoi batchKoi = new BatchKoi();
//        batchKoi.setBatchKoiID(batchKoiRequest.getBatchKoiID());
        batchKoi.setName(batchKoiRequest.getName());
        batchKoi.setPrice(batchKoiRequest.getPrice());
        batchKoi.setQuantity(batchKoiRequest.getQuantity());
        batchKoi.setDescription(batchKoiRequest.getDescription());

        KoiSpecies koiSpecies = koiSpeciesRepository.findKoiSpeciesById(batchKoiRequest.getSpeciesId());
        if (koiSpecies == null){
            throw new EntityNotFoundException("Koi species not found!");
        }
        batchKoi.setSpecies(koiSpecies);

        return batchKoiRepository.save(batchKoi);

    }

    public List<BatchKoi> getAllBatchKoi(){
        List<BatchKoi> batchKoiList = batchKoiRepository.findBatchKoiByIsDeletedFalse();
        return batchKoiList;
    }

    //get
    public BatchKoi getBatchKoiById(UUID batchKoiID){
        BatchKoi oldBatchKoi = batchKoiRepository.findBatchKoiBybatchKoiID(batchKoiID);
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
