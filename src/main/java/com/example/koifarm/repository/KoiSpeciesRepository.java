package com.example.koifarm.repository;

import com.example.koifarm.entity.KoiSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoiSpeciesRepository extends JpaRepository<KoiSpecies, Long> {
    KoiSpecies findKoiSpeciesById(long id);
}
