package com.example.koifarm.service;

import com.example.koifarm.entity.Koi;
import com.example.koifarm.repository.KoiRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiService {
    @Autowired
    KoiRepository koiRepository;

    //create
    public Koi create(Koi koi){
        Koi newKoi = koiRepository.save(koi);
        return newKoi;
    }

    //read
    public List<Koi> getAllKoi(){
        List<Koi> kois = koiRepository.findKoiByIsDeletedFalse();
        return  kois;
    }

    //Update
    public Koi update(long koiID, Koi koi){
        //b1: tim Koi can duoc update
        Koi oldKoi = getKoiById(koiID);

        //b2: cap nhat thong tin cua no
        oldKoi.setName(koi.getName());
        oldKoi.setAge(koi.getAge());
        oldKoi.setSize(koi.getSize());

        //b3: luu xuong DB
        return koiRepository.save(oldKoi);
    }

    //Delete
    public Koi delete(long koiID){
        Koi oldKoi = getKoiById(koiID);
        oldKoi.setDeleted(true);
        return koiRepository.save(oldKoi);
    }

    public Koi getKoiById(long koiID){
        Koi oldKoi = koiRepository.findKoiByKoiID(koiID);
        if (oldKoi == null) throw new EntityNotFoundException("Koi not found!");
        return oldKoi;
    }


}
