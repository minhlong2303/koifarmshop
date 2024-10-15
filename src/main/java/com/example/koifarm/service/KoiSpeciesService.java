package com.example.koifarm.service;

import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.KoiSpeciesRequest;
import com.example.koifarm.repository.KoiSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class KoiSpeciesService {

    @Autowired
    KoiSpeciesRepository koiSpeciesRepository;

    public KoiSpecies createKoiSpecies(KoiSpeciesRequest koiSpeciesRequest){
        KoiSpecies koiSpecies = new KoiSpecies();
        koiSpecies.setName(koiSpeciesRequest.getName());
        koiSpecies.setDescription(koiSpeciesRequest.getDescription());
        return koiSpeciesRepository.save(koiSpecies);
    }

    public List<KoiSpecies> getAllKoiSpecies(){
        List<KoiSpecies> koiSpeciesList = koiSpeciesRepository.findKoiSpeciesByIsDeletedFalse();
        return koiSpeciesList;
    }

    public KoiSpecies update(long id, KoiSpecies koiSpecies){
        KoiSpecies oldSpecies = getKoiSpeciesById(id);
        oldSpecies.setName(koiSpecies.getName());
        oldSpecies.setDescription(koiSpecies.getDescription());
        return koiSpeciesRepository.save(oldSpecies);
    }

    public KoiSpecies delete(long id){
        KoiSpecies olSpecies = getKoiSpeciesById(id);
        olSpecies.setDeleted(true);
        return koiSpeciesRepository.save(olSpecies);
    }

    public KoiSpecies getKoiSpeciesById(long id){
        KoiSpecies oldKoiSpecies = koiSpeciesRepository.findKoiSpeciesById(id);

        if (oldKoiSpecies == null)
            throw new EntityNotFoundException("Koi Species not found!");
        return oldKoiSpecies;
    }





}
