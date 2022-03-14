package com.mercadolibre.animalia.models.response;

import com.mercadolibre.animalia.models.Citizen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebhookCitizensResponse {
    private String Topic;
    private String Method;
    List<Citizen> body;


}