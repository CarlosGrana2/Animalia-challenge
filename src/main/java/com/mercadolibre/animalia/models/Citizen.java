package com.mercadolibre.animalia.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
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
    @ApiModelProperty(notes = "Id Ciudadano",name="id",required=true,value="1")
    private long id;

    @Column(unique = true)
    @NonNull
    @ApiModelProperty(notes = "Identificador del Ciudadano (Este valor es único por usuario)",name="Indentificador",required=true,value="108293568485")
    private String identifier;
    @NonNull
    @ApiModelProperty(notes = "Nombre del Ciudadano",name="Nombre",required=true,value="Pepito Perez")
    private String name;

    @ManyToOne
    @JoinColumn(name = "specie_id")
    @ApiModelProperty(notes = "Id de especie (Este valor debe ser tomado del servicio de especie)",name="Especie",required=true,value="1")
    private Specie species;


    @NonNull
    @ApiModelProperty(notes = "Descripción del Ciudadano",name="Descripción",required=true,value="Pepito Perez ciudadano de el planeta plutón")
    private String description;
    @ApiModelProperty(notes = "Altura del Ciudadano en metros",name="Altura",required=true,value="1.5")
    private String height;
    @ApiModelProperty(notes = "Peso del Ciudadano en kilogramos",name="Peso",required=true,value="85")
    private String weight;
    @ApiModelProperty(notes = "url de foto del ciudadano ",name="url foto",required=true,value="www.miimagen.com/photo.jpg")
    private String photo;


    @NonNull
    @ApiModelProperty(notes = "Indicador de si el Ciudadano tiene mascota",name="Tiene mascota",required=true,value="SI/NO")
    private String pet;

    @Temporal(TemporalType.TIMESTAMP)
    @ApiModelProperty(notes = "Fecha de alta del ciudadano",name="Fecha de alta",required=true,value="timestamp")
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
    @ApiModelProperty(notes = "Id del rol (Este campo debe ser tomado del servicio obtenerRoles)",name="Roles",required=true,value="1")
    private Set<Role> roles;
}
