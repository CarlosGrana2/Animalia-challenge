package com.mercadolibre.animalia.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.animalia.models.Webhooks;
import com.mercadolibre.animalia.models.response.WebhookCitizensResponse;
import lombok.extern.slf4j.Slf4j;
import com.mercadolibre.animalia.models.Citizen;
import com.mercadolibre.animalia.models.Role;
import com.mercadolibre.animalia.models.Specie;
import com.mercadolibre.animalia.models.request.CreateCitizens;
import com.mercadolibre.animalia.models.response.CitizenzPaginatedResponse;
import com.mercadolibre.animalia.repositories.CitizensRepository;
import com.mercadolibre.animalia.repositories.RolesRepository;
import com.mercadolibre.animalia.repositories.SpecieRepository;
import com.mercadolibre.animalia.repositories.WebhookRepository;
import com.mercadolibre.animalia.spec.CitizenSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitizenServices {
    private final CitizensRepository citizensRepository;
    private final RolesRepository rolesRepository;
    private final SpecieRepository specieRepository;
    private final WebhookRepository webhookRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Transactional
    public List<Citizen> createCitizens(List<CreateCitizens> citizen) {

        List<Citizen> listCitizen = new ArrayList<>();
        for (CreateCitizens cit : citizen) {
            if (findByIdentifer(cit.getIdentifier())) {
                Set<Role> roles = new HashSet<>();
                cit.getRoles().forEach(role -> {
                    Optional<Role> rol = rolesRepository.findById(role.getId_role());
                    if (!rol.isPresent()) {
                        throw new IllegalArgumentException("Rol :" + role.getId_role() + " no existe");
                    }
                    roles.add(rol.get());
                });

                Optional<Specie> specie = specieRepository.findById(cit.getSpecies());
                if (!specie.isPresent()) {
                    throw new IllegalArgumentException("Specie :" + cit.getSpecies() + "no existe");
                }

                Citizen Item = new Citizen();
                Item.setHigh_date(new Date());
                Item.setRoles(roles);
                Item.setSpecies(specie.get());
                BeanUtils.copyProperties(cit, Item);
                listCitizen.add(Item);
            }
            validateRangeRolAndUniqueRole(listCitizen);

        }

        List<Citizen> response = citizensRepository.saveAll(listCitizen);
        processMessagesForDestination(response, "Citizens", "POST");
        return response;

    }

    @Transactional
    public Citizen createCitizen(CreateCitizens citizen) {
        Set<Role> roles = new HashSet<>();
        if (findByIdentifer(citizen.getIdentifier())) {

            citizen.getRoles().forEach(role -> {
                Optional<Role> rol = rolesRepository.findById(role.getId_role());
                if (!rol.isPresent()) {
                    throw new IllegalArgumentException("Rol no existe");
                }
                roles.add(rol.get());
            });
        }

        Optional<Specie> specie = specieRepository.findById(citizen.getSpecies());
        if (!specie.isPresent()) {
            throw new IllegalArgumentException("Especie " + citizen.getSpecies() + " no existe");
        }

        Citizen citizenNew = new Citizen();
        citizenNew.setRoles(roles);
        citizenNew.setSpecies(specie.get());
        citizenNew.setHigh_date(new Date());
        BeanUtils.copyProperties(citizen, citizenNew);
        validateUpdateRolesCitizens(citizenNew);
        Citizen response =  citizensRepository.save(citizenNew);

        List<Citizen> citizensnotification =  new ArrayList<Citizen>();
        citizensnotification.add(response);

        processMessagesForDestination(citizensnotification,"Citizens", "POST");
        return response;

    }

    @Transactional(readOnly = true)
    public Citizen findById(Long id) {
        Optional<Citizen> opt = citizensRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("No existe usuario con el id: " + id);
        }
        return opt.get();
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Citizen citizen = findById(id);
        Map<String, Object> response = new HashMap<>();

        if (citizen == null) {
            throw new IllegalArgumentException("no se puede eliminar, el ciudadano con el ID: " + id + "No existe");
        } else {
            citizensRepository.deleteById(id);
        }
        response.put("mensaje", "Ciudadano eliminado con exito!!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public Citizen updateCitizen(Long id, CreateCitizens citizen) {
        Citizen citizenUpdate = findById(id);
        Set<Role> roles = new HashSet<>();

        citizen.getRoles().forEach(role -> {
            Optional<Role> rol = rolesRepository.findById(role.getId_role());
            if (!rol.isPresent()) {
                throw new IllegalArgumentException("Rol No existe");
            }
            roles.add(rol.get());
        });
        citizenUpdate.setRoles(roles);
        validateUpdateRolesCitizens(citizenUpdate);
        BeanUtils.copyProperties(citizen, citizenUpdate);
        return citizensRepository.save(citizenUpdate);
    }

    @Transactional(readOnly = true)
    public CitizenzPaginatedResponse findAllCitizens(String name,
                                                     String description,
                                                     List<String> rolcode,
                                                     Integer height,
                                                     Integer weight,
                                                     String identifier,
                                                     String havepets,
                                                     List<String> especiecode,
                                                     Pageable pageable) {

        Specification<Citizen> spec = CitizenSpecs.getSpecs(name, description, rolcode, height, weight, identifier, havepets, especiecode);
        Page<Citizen> citizen = citizensRepository.findAll(spec, pageable);

        return CitizenzPaginatedResponse.builder()
                .numberOfItems(citizen.getTotalElements()).numberOfPage(citizen.getTotalPages())
                .citizens(citizen.getContent())
                .build();
    }

    private Boolean findByIdentifer(String identifier) {
        Optional<Citizen> opt = citizensRepository.findByIdentifier(identifier);
        if (opt.isPresent()) {
            throw new DuplicateKeyException("Ya existe usuario con el identificador: " + identifier + " , Verifique!!");
        }
        return true;
    }

    private boolean validateRangeRolAndUniqueRole(List<Citizen> citizens) {

        List<Long> rolesUnique = new ArrayList<Long>();

        for (Citizen rol : citizens) {
            List<Integer> rangeForCit = new ArrayList<Integer>();
            rol.getRoles().forEach(role -> {
                rangeForCit.add(role.getRange_rol());
                if (role.getUnique_rol() == 1 && role.getCitizens().size() > 0) {
                    throw new IllegalArgumentException("ya existe un ciudadano con rol " + role.getDescription() + " , Este rol es único en el sistema");
                } else if (rolesUnique.contains(role.getId())) {
                    throw new IllegalArgumentException("EL listado de usuarios contiene dos o mas usuarios con el rol: " + role.getDescription() + " , Este rol debe único en el sistema, Verifique!!");
                }
                if (role.getUnique_rol() == 1) {
                    rolesUnique.add(role.getId());
                }
            });

            if (rangeForCit.size() > 1) {
                if (rangeForCit.contains(3)) {
                    throw new IllegalArgumentException("Está intentando agregar el usuario " + rol.getName() + " con el rol Civil, Este no debe tener otro rol con rango superior, Verifique!! ");
                }
            }
        }
        return true;
    }

    private boolean validateUpdateRolesCitizens(Citizen citizen) {

        List<Long> rolesUnique = new ArrayList<Long>();
        List<Integer> rangeForCit = new ArrayList<Integer>();

        citizen.getRoles().forEach(role -> {
            rangeForCit.add(role.getRange_rol());
            if (role.getUnique_rol() == 1 && role.getCitizens().size() > 0) {
                role.getCitizens().forEach(cit -> {
                    if (cit.getId() != citizen.getId()) {
                        throw new IllegalArgumentException("ya existe un ciudadano con rol " + role.getDescription() + " , Este rol es único en el sistema");
                    }
                });
            } else if (rolesUnique.contains(role.getId())) {
                throw new IllegalArgumentException("EL listado de usuarios contiene dos o mas usuarios con el rol: " + role.getDescription() + " , Este rol debe único en el sistema, Verifique!!");
            }
            if (role.getUnique_rol() == 1) {
                rolesUnique.add(role.getId());
            }
        });

        if (rangeForCit.size() > 1) {
            if (rangeForCit.contains(3)) {
                throw new IllegalArgumentException("Está intentando actualizar el usuario " + citizen.getName() + " con el rol Civil, Este no debe tener otro rol con rango superior, Verifique!! ");
            }
        }
        return true;
    }


    private void processMessagesForDestination(List<Citizen> citizen, String topic, String method) {
        List<Webhooks> destinations = webhookRepository.findAll();
        for (Webhooks web : destinations) {
            sendNotification(web.getUrl_notification(), citizen, topic ,method);
        }
    }


    private void sendNotification(String url, List<Citizen> response , String topic, String method) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
                        WebhookCitizensResponse  webhookCitizensResponse =  new WebhookCitizensResponse();
            webhookCitizensResponse.setTopic(topic);
            webhookCitizensResponse.setMethod(method);
            webhookCitizensResponse.setBody(response);
            headers.setContentType(MediaType.APPLICATION_JSON);
            Thread.sleep(500);
            HttpEntity<WebhookCitizensResponse> request =
                    new HttpEntity<WebhookCitizensResponse>(webhookCitizensResponse, headers);

            String result = restTemplate.postForObject(url, request, String.class);
            JsonNode root = objectMapper.readTree(result);

        } catch (Exception ex) {
            log.error("sendMessage caught an exception: {}" , ex.getMessage());
        }
    }
}
