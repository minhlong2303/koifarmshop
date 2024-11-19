package com.example.koifarm.service;

import com.example.koifarm.entity.Koi;
import com.example.koifarm.entity.KoiSpecies;
import com.example.koifarm.entity.User;
import com.example.koifarm.exception.EntityNotFoundException;
import com.example.koifarm.model.KoiRequest;
import com.example.koifarm.repository.KoiRepository;
import com.example.koifarm.repository.KoiSpeciesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KoiService {

    @Autowired
    private KoiRepository koiRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private KoiSpeciesRepository koiSpeciesRepository;

    // Create a new Koi
    public Koi create(KoiRequest koiRequest) {
        Koi koi = modelMapper.map(koiRequest, Koi.class);

        // Set the creator (current user) for the Koi
        User user = authenticationService.getCurrentUser();
        koi.setUser(user);

        // Fetch the Koi species
        KoiSpecies koiSpecies = koiSpeciesRepository.findKoiSpeciesById(koiRequest.getSpeciesId());
        if (koiSpecies == null) {
            throw new EntityNotFoundException("Koi Species not found!");
        }
        koi.setSpecies(koiSpecies);

        // Save and return the new Koi
        return koiRepository.save(koi);
    }

    // Read all Koi
    public List<Koi> getAllKoi() {
        return koiRepository.findKoiByIsDeletedFalse(); // Fetch only non-deleted Koi
    }

    // Update an existing Koi
    public Koi update(UUID koiID, Koi koi) {
        Koi oldKoi = getKoiById(koiID); // Find the Koi to update

        // Update the Koi properties
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

        // Save the updated Koi
        return koiRepository.save(oldKoi);
    }

    // Soft delete a Koi
    public Koi delete(UUID koiID) {
        Koi oldKoi = getKoiById(koiID);
        oldKoi.setDeleted(true); // Mark as deleted
        return koiRepository.save(oldKoi); // Save the updated Koi
    }

    // Get a Koi by its ID
    public Koi getKoiById(UUID koiID) {
        return koiRepository.findKoiByKoiID(koiID)
                .orElseThrow(() -> new EntityNotFoundException("Koi not found!"));
    }
}