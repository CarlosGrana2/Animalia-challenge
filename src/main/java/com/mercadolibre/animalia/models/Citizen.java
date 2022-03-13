package com.mercadolibre.animalia.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @Column(name = "citizen_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @NonNull
    private String identifier;
    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "specie_id")
    private Specie species;


    @NonNull
    private String description;
    private String height;
    private String weight;
    private String photo;


    @NonNull
    private String pet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date high_date;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="citizens_roles",
    joinColumns = {@JoinColumn(name = "fk_citizen_id",referencedColumnName = "citizen_id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_role_id", referencedColumnName = "role_id")}
    )
    private Set<Role> roles;
}
