package com.mercadolibre.animalia.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.models.Status;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Date;
import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RolesAcceptanceTest {

    @LocalServerPort
    String ServerPort = "8080";

   private RestTemplate restTemplate;

    private Role roles;

    private String url;

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
    public void shouldGetRolesByIdCreated() throws Exception {
        restTemplate = new RestTemplate();
        url = "https://app-animalia-mercadolibre.herokuapp.com/api/roles/1";
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


    @Test
    public void shouldGetRoleCreated() throws Exception {
        restTemplate = new RestTemplate();
        url = "https://app-animalia-mercadolibre.herokuapp.com/api/roles/getAllRoles";
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
