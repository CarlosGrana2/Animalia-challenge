package com.mercadolibre.animalia.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.animalia.controllers.RolesCitizensController;
import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(RolesCitizensController.class)
public class RolesServicesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    public void Get_All_Roles_And_Return_an_array() throws Exception {
        List<Role> allRoles = Arrays.asList(roles);
        given(rolesservices.getAllRoles()).willReturn(allRoles);

        mockMvc.perform(get("/api/roles/getAllRoles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description", is(roles.getDescription())));
    }

    @Test
    public void Create_Role_And_Return_an_array() throws Exception {

        given(rolesservices.createRoles(roles)).willReturn(roles);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/roles/createrol")
                        .content(asJsonString(roles))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
