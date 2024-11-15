package com.example.koifarm.repository;

import com.example.koifarm.entity.KoiSpecies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KoiSpeciesRepository extends JpaRepository<KoiSpecies, Long> {
    KoiSpecies findKoiSpeciesById(long id);

    List<KoiSpecies> findKoiSpeciesByIsDeletedFalse();

}
