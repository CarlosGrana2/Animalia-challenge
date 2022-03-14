package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Citizen;
import com.mercadolibre.animalia.models.request.CreateCitizens;
import com.mercadolibre.animalia.services.CitizenServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Ciudadanos rest controller", description = "REST API para ciudadanos")
@RequestMapping(value = "/api/citizens")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CitizensController {

    private final CitizenServices citizensService;


    @ApiOperation(value = "Crear Ciudadanos ", response = Iterable.class, tags = "Api Rest Ciudadanos")
    @PostMapping("/createcitezens")
    public List<Citizen> saveCitizens(@Valid @RequestBody List<CreateCitizens> request, BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        log.info("Iniciando creaciones de citizens" + request);
        List<Citizen> citizenResponse = (List<Citizen>) citizensService.createCitizens(request);
        return citizenResponse;
    }

    @ApiOperation(value = "Crear Ciudadano ", response = Iterable.class, tags = "Api Rest Ciudadanos")
    @PostMapping("/createcitezen")
    public Citizen saveCitizen(@Valid @RequestBody CreateCitizens request, BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }

        Citizen response = citizensService.createCitizen(request);
        return response ;
    }


    @ApiOperation(value = "Obtener Ciudadano por Id ", response = Iterable.class, tags = "Api Rest Ciudadanos")
    @GetMapping("/{id}")
    public Citizen getCitizenById(@PathVariable("id") long id) {
        return citizensService.findById(id);
    }

    @ApiOperation(value = "Obtener Ciudadanos por filtros ", response = Iterable.class, tags = "Api Rest Ciudadanos")
    @GetMapping("/getallcitizens")
    public ResponseEntity getAllCitizens(@RequestParam(required = false, defaultValue = "0", value = "page") Integer pageNumber,
                                         @RequestParam(required = false, defaultValue = "25", value = "size") Integer pageSize,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) List<String> rolcode,
                                         @RequestParam(required = false) String description,
                                         @RequestParam(required = false) Integer height,
                                         @RequestParam(required = false) Integer weight,
                                         @RequestParam(required = false) String identifier,
                                         @RequestParam(required = false) String havepets,
                                         @RequestParam(required = false) List<String> speciecode) {
        Pageable request = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(citizensService.findAllCitizens(name, description, rolcode, height, weight, identifier, havepets, speciecode, request));
    }

    @ApiOperation(value = "Eliminar Ciudadanos ", response = Iterable.class, tags = "Api Rest Ciudadanos")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCitizen(@PathVariable("id") long id) {
        return citizensService.delete(id);
    }

    @ApiOperation(value = "Actualizar Ciudadanos por id ", response = Iterable.class, tags = "Api Rest Ciudadanos")
    @PutMapping("/update/{id}")
    public Citizen updateCitizens(@PathVariable("id") long id, @Valid @RequestBody CreateCitizens request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return citizensService.updateCitizen(id, request);
    }



}
