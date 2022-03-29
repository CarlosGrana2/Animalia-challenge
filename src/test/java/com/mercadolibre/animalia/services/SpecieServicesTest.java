package com.mercadolibre.animalia.services;

import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.models.Specie;
import com.mercadolibre.animalia.models.Status;
import com.mercadolibre.animalia.repositories.SpecieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SpecieServicesTest {

    @Mock
    private SpecieRepository specieRepository;

    @InjectMocks
    private SpecieServices specieServices;

    private Specie specie;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        specie = new Specie();
        specie.setId(11L);
        specie.setDescription("ejemplo");
        specie.setStatus(Status.ACTIVE);
    }

    @Test
    void createSpecie() {
        when(specieRepository.save(any(Specie.class))).thenReturn(specie);
        assertNotNull(specieServices.createSpecie(specie));
    }

    @Test
    void getAllSpecie() {
        when(specieRepository.findAll()).thenReturn(Arrays.asList(specie));
        assertNotNull(specieServices.getAllSpecie());
    }

    @Test
    void findById() {
        when(specieRepository.findById(11L)).thenReturn(Optional.of(specie));
        assertNotNull(specieServices.findById(11L));
    }
}