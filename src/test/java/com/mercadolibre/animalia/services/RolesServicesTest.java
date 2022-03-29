package com.mercadolibre.animalia.services;

import com.mercadolibre.animalia.models.Citizen;
import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.models.Status;
import com.mercadolibre.animalia.repositories.CitizensRepository;
import com.mercadolibre.animalia.repositories.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RolesServicesTest {

    @Mock
    private RolesRepository rolesrepository;

    @InjectMocks
    private RolesServices rolesservices;

    private Role roles;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        roles = new Role();
        roles.setId(11L);
        roles.setStatus(Status.ACTIVE);
        roles.setDescription("Ejemplo rol");
        roles.setRange_rol(3);
        roles.setDate_create_rol(new Date());

    }

    @Test
    void createRoles() {
        when(rolesrepository.save(any(Role.class))).thenReturn(roles);
        assertNotNull(rolesservices.createRoles(roles));
    }

    @Test
    void getAllRoles() {
        when(rolesrepository.findAll()).thenReturn(Arrays.asList(roles));
        assertNotNull(rolesservices.getAllRoles());
    }

    @Test
    void findById() {
        when(rolesrepository.findById(11L)).thenReturn(Optional.of(roles));
        assertNotNull(rolesservices.findById(11L));
    }

}