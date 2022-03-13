package com.mercadolibre.animalia.services;

import com.mercadolibre.animalia.models.Specie;
import com.mercadolibre.animalia.repositories.SpecieRepository;
import org.springframework.beans.BeanUtils;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class SpecieServices {
    private final SpecieRepository specieRepository;

    @Transactional
    public Specie createSpecie (Specie specie){
        Specie specieCreate = new Specie();
        BeanUtils.copyProperties(specie,specieCreate);
        return specieRepository.save(specieCreate);
    }

    public List<Specie> getAllSpecie(){
        return specieRepository.findAll();
    }

    public Specie findById(Long id) {
        Optional<Specie> opt = specieRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("No existe Especie con el id: " + id);
        }
        return opt.get();
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Specie role = findById(id);
        Map<String, Object> response = new HashMap<>();

        if (role == null) {
            throw new IllegalArgumentException("no se puede eliminar, la especie con el ID: " + id + "No existe");
        } else {
            specieRepository.deleteById(id);
        }
        response.put("mensaje", "Especie eliminada con exito!!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public Specie updateSpecie(Long id, Specie specie) {
        Specie specieUpdate = findById(id);
        specie.setId(id);
        BeanUtils.copyProperties(specie, specieUpdate);
        return specieRepository.save(specieUpdate);
    }

}
