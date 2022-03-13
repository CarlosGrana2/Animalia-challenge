package com.mercadolibre.animalia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String description;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    private int unique_rol;

    @NonNull
    @Min(value = 0, message = "El rango debe ser igual o mayor 0 y menor igual que 3")
    @Max(value = 3, message = "El rango debe ser igual o mayor 0 y menor igual que 3")
    private int range_rol;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_create_rol;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Citizen> citizens;
}
