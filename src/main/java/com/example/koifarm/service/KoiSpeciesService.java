package com.example.koifarm.service;

import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.model.KoiSpeciesRequest;
import com.example.koifarm.repository.KoiSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
