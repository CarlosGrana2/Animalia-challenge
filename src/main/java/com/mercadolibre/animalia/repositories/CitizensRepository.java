package com.mercadolibre.animalia.repositories;

import com.mercadolibre.animalia.models.Citizen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizensRepository extends JpaRepository<Citizen, Long> {
    Optional<Citizen> findByIdentifier(String Identifier);
    Page<Citizen> findAll(Specification<Citizen> spec, Pageable pageable);
}
