package com.mercadolibre.animalia.services;

import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.repositories.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Service
@RequiredArgsConstructor
public class RolesServices {

    private final RolesRepository rolesRepository;

    @Transactional
    public Role createRoles (Role roles){
        Role roleCreate = new Role();
        roles.setDate_create_rol(new Date());
        if (roles.getRange_rol() < 0 && roles.getRange_rol() > 3){
            throw new IllegalArgumentException("Rango no permitido, ver la documentación de la api ");
        }
        BeanUtils.copyProperties(roles,roleCreate);
        return rolesRepository.save(roleCreate);
    }

    public List<Role> getAllRoles(){
        return rolesRepository.findAll();
    }

    public Role findById(Long id) {
        Optional<Role> opt = rolesRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("No existe Rol con el id: " + id);
        }
        return opt.get();
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Role role = findById(id);
        Map<String, Object> response = new HashMap<>();

        if (role == null) {
            throw new IllegalArgumentException("no se puede eliminar, el Rol con el ID: " + id + "No existe");
        } else {
            rolesRepository.deleteById(id);
        }
        response.put("mensaje", "Rol eliminado con exito!!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public Role updateRol(Long id, Role rol) {
        Role rolUpdate = findById(id);
        rol.setDate_create_rol(new Date());
        rol.setId(id);
        BeanUtils.copyProperties(rol, rolUpdate);
        return rolesRepository.save(rolUpdate);
    }

}
