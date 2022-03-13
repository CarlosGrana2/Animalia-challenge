package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Citizen;
import com.mercadolibre.animalia.models.request.CreateCitizens;
import com.mercadolibre.animalia.services.CitizenServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/api/citizens")
@RestController
@RequiredArgsConstructor
public class CitizensController {

    private final CitizenServices citizensService;

    @PostMapping("/createcitezens")
    public List<Citizen> saveCitizens(@Valid @RequestBody List<CreateCitizens> request, BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        List<Citizen> citizenResponse = (List<Citizen>) citizensService.createCitizens(request);
        return citizenResponse;
    }

    @PostMapping("/createcitezen")
    public Citizen saveCitizen(@Valid @RequestBody CreateCitizens request, BindingResult result) {

        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return citizensService.createCitizen(request);
    }


    @GetMapping("/{id}")
    public Citizen getCitizenById(@PathVariable("id") long id) {
        return citizensService.findById(id);
    }

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


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCitizen(@PathVariable("id") long id) {
        return citizensService.delete(id);
    }

    @PutMapping("/update/{id}")
    public Citizen updateCitizens(@PathVariable("id") long id, @Valid @RequestBody CreateCitizens request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return citizensService.updateCitizen(id, request);
    }

}
