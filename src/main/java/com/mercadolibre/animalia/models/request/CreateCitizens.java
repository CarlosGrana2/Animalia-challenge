package com.mercadolibre.animalia.models.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateCitizens {
    private String identifier;
    private String name;
    private long species;
    private String description;
    private String height;
    private String  weight;
    private String  photo;
    private String pet;
    private List<RolesCitizens> roles;
}
