package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Specie;
import com.mercadolibre.animalia.services.SpecieServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Especies rest controller", description = "REST API para administración de especies")
@RequestMapping(value = "/api/species")
@RestController
@RequiredArgsConstructor
public class SpeciesController {
    private final SpecieServices specieServices;

    @ApiOperation(value = "Crear nueva especie ", response = Iterable.class, tags = "Api Rest Especies")
    @PostMapping("/createnew")
    public Specie saveRoles(@Valid @RequestBody Specie request, BindingResult result){
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }
        return specieServices.createSpecie(request);
    }

    @ApiOperation(value = "Obtener todas las especies", response = Iterable.class, tags = "Api Rest Especies")
    @GetMapping("/getAllSpecies")
    public ResponseEntity getAllSpecies(){
        return ResponseEntity.ok(specieServices.getAllSpecie());
    }

    @ApiOperation(value = "Obtener especies por ids", response = Iterable.class, tags = "Api Rest Especies")
    @GetMapping("/{id}")
    public Specie getSpecieById(@PathVariable("id") long id){
        return specieServices.findById(id);
    }

    @ApiOperation(value = "Eliminar especie", response = Iterable.class, tags = "Api Rest Especies")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpecie(@PathVariable("id") long id) {
        return specieServices.delete(id);
    }

    @ApiOperation(value = "Actualizar especie", response = Iterable.class, tags = "Api Rest Especies")
    @PutMapping("/update/{id}")
    public Specie updateSpecie(@PathVariable("id") long id, @Valid @RequestBody Specie request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return specieServices.updateSpecie(id, request);
    }



}
