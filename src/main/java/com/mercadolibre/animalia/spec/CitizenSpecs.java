package com.mercadolibre.animalia.spec;

import com.mercadolibre.animalia.models.Citizen;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CitizenSpecs {
    public static Specification<Citizen> getSpecs(String name, String description,
                                                  List<String> rol,
                                                  Integer height,
                                                  Integer weight,
                                                  String identifier,
                                                  String havepets,
                                                  List<String> specie) {

        Specification<Citizen> spec = null;
        Specification<Citizen> temp = null;

        if (name != null) {
            temp = getCitizenNname(name);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (description != null) {
            temp = getCitizenDescription(description);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (rol != null) {
            temp = getCitizenRol(rol);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (height != null) {
            temp = getCitizenHeight(height);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (weight != null) {
            temp = getCitizenWeight(weight);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (identifier != null) {
            temp = getCitizenIdentifier(identifier);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (havepets != null) {
            temp = getCitizenHavepets(havepets);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }
        if (specie != null) {
            temp = getCitizenSpecie(specie);
            spec = temp != null ? Specification.where(spec).and(temp) : temp;
        }

        return spec;
    }

    private static Specification<Citizen> getCitizenSpecie(List<String> specie) {
        List<Predicate> predicates = new ArrayList<>();
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            if ((specie == null)) {
                return null;
            }
            for (String speciesearch : specie) {
                predicates.add(criteriaBuilder.equal(root.join("species").get("description"), speciesearch ));
            }
            return criteriaBuilder.or(
                    predicates.toArray(new Predicate[0])
            );
        });
    }

    private static Specification<Citizen> getCitizenHavepets(String havepets) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("pet"), havepets));
    }

    private static Specification<Citizen> getCitizenIdentifier(String identifier) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("identifier"), identifier));
    }

    private static Specification<Citizen> getCitizenWeight(Integer weight) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.toInteger(root.get("weight")), weight));
    }

    private static Specification<Citizen> getCitizenHeight(Integer height) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.toInteger(root.get("height")), height));
    }

    private static Specification<Citizen> getCitizenRol(List<String> rol) {
        List<Predicate> predicates = new ArrayList<>();
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            if ((rol == null)) {
                return null;
            }
            for (String rolsearch : rol) {
                predicates.add(criteriaBuilder.equal(root.join("roles").get("id"),  rolsearch ));
            }
            return criteriaBuilder.or(
                    predicates.toArray(new Predicate[0])
            );
        });
    }

    private static Specification<Citizen> getCitizenDescription(String description) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
    }

    private static Specification<Citizen> getCitizenNname(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }
}
