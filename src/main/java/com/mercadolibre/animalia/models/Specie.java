package com.mercadolibre.animalia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "species")
public class Specie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String description;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;


}
