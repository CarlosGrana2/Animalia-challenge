package com.mercadolibre.animalia.repositories;

import com.mercadolibre.animalia.models.Specie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecieRepository extends JpaRepository<Specie, Long> {
}
