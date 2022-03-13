package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Specie;
import com.mercadolibre.animalia.services.SpecieServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/species")
@RestController
@RequiredArgsConstructor
public class SpeciesController {
    private final SpecieServices specieServices;

    @PostMapping("/createnew")
    public Specie saveRoles(@Valid @RequestBody Specie request, BindingResult result){
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }
        return specieServices.createSpecie(request);
    }

    @GetMapping("/getAllSpecies")
    public ResponseEntity getAllSpecies(){
        return ResponseEntity.ok(specieServices.getAllSpecie());
    }

    @GetMapping("/{id}")
    public Specie getSpecieById(@PathVariable("id") long id){
        return specieServices.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSpecie(@PathVariable("id") long id) {
        return specieServices.delete(id);
    }

    @PutMapping("/update/{id}")
    public Specie updateSpecie(@PathVariable("id") long id, @Valid @RequestBody Specie request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return specieServices.updateSpecie(id, request);
    }



}
