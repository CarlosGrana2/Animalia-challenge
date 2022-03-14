package com.mercadolibre.animalia.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
    @ApiModelProperty(notes = "Id rol",name="id",required=true,value="1")
    private Long id;

    @NonNull
    @ApiModelProperty(notes = "Descripción o nombre del rol",name="descripción",required=true,value="General/Civil...")
    private String description;

    @NonNull
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Estado del rol",name="estado",required=true,value="ACTIVE,DEACTIVATED")
    private Status status;

    @NonNull
    @ApiModelProperty(notes = "Indica si solo puede haber 1 activo",name="estado",required=true,value="1(acitvo),0(inactivo)")
    private int unique_rol;

    @NonNull
    @Min(value = 0, message = "El rango debe ser igual o mayor 0 y menor igual que 3")
    @Max(value = 3, message = "El rango debe ser igual o mayor 0 y menor igual que 3")
    @ApiModelProperty(notes = "Indica el rango que tiene el rol en el sistema",name="Rango",required=true,value="0(Superior),1(Lider),2(CoLider),3(Civil)")
    private int range_rol;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_create_rol;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Citizen> citizens;
}
