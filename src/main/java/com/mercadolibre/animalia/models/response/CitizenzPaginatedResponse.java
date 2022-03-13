package com.mercadolibre.animalia.models.response;

import com.mercadolibre.animalia.models.Citizen;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CitizenzPaginatedResponse {
    private List<Citizen> citizens;
    private Long numberOfItems;
    private int numberOfPage;
}
