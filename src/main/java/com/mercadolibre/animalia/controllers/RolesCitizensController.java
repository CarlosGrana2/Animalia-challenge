package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.services.RolesServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Roles rest controller", description = "REST API para roles")
@RequestMapping(value = "/api/roles")
@RestController
@RequiredArgsConstructor
public class RolesCitizensController {

    private final RolesServices rolesServices;

    @ApiOperation(value = "Crear Rol ", response = Iterable.class, tags = "Api Rest Roles")
    @PostMapping("/createrol")
    public Role saveRoles(@Valid @RequestBody Role request, BindingResult result){
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }
        return rolesServices.createRoles(request);
    }

    @ApiOperation(value = "Obtener todos Rol ", response = Iterable.class, tags = "Api Rest Roles")
    @GetMapping("/getAllRoles")
    public ResponseEntity getAllRoles(){
        return ResponseEntity.ok(rolesServices.getAllRoles());
    }

    @ApiOperation(value = "Obtener Rol por Id ", response = Iterable.class, tags = "Api Rest Roles")
    @GetMapping("/{id}")
    public Role getRolById(@PathVariable("id") long id){
        return rolesServices.findById(id);
    }

    @ApiOperation(value = "Eliminar Rol ", response = Iterable.class, tags = "Api Rest Roles")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable("id") long id) {
        return rolesServices.delete(id);
    }

    @ApiOperation(value = "Actualizar Rol ", response = Iterable.class, tags = "Api Rest Roles")
    @PutMapping("/update/{id}")
    public Role updateRol(@PathVariable("id") long id, @Valid @RequestBody Role request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return rolesServices.updateRol(id, request);
    }


}
