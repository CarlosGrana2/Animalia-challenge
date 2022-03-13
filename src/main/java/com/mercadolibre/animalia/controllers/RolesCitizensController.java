package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.services.RolesServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/roles")
@RestController
@RequiredArgsConstructor
public class RolesCitizensController {

    private final RolesServices rolesServices;
    public Role roles;

    @PostMapping("/add")
    public Role saveRoles(@Valid @RequestBody Role request, BindingResult result){
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }
        return rolesServices.createRoles(request);
    }

    @GetMapping("/getAllRoles")
    public ResponseEntity getAllRoles(){
        return ResponseEntity.ok(rolesServices.getAllRoles());
    }

    @GetMapping("/{id}")
    public Role getRolById(@PathVariable("id") long id){
        return rolesServices.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable("id") long id) {
        return rolesServices.delete(id);
    }

    @PutMapping("/update/{id}")
    public Role updateRol(@PathVariable("id") long id, @Valid @RequestBody Role request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return rolesServices.updateRol(id, request);
    }


}
