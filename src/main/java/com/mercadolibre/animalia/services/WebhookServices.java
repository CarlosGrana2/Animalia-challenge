package com.mercadolibre.animalia.services;

import com.mercadolibre.animalia.models.Webhooks;
import com.mercadolibre.animalia.repositories.WebhookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WebhookServices {

    private final WebhookRepository webhookRepository;

    @Transactional
    public Webhooks createUrlNotification (Webhooks webhooks){
        Webhooks webhooksCreate = new Webhooks();
        webhooks.setDate_create_webhook(new Date());
        BeanUtils.copyProperties(webhooks,webhooksCreate);
        return webhookRepository.save(webhooksCreate);
    }

    public List<Webhooks> getAllWebhook(){
        return webhookRepository.findAll();
    }

    public Webhooks findById(Long id) {
        Optional<Webhooks> opt = webhookRepository.findById(id);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("No existe webhook con el id: " + id);
        }
        return opt.get();
    }

    @Transactional
    public ResponseEntity<?> delete(Long id) {
        Webhooks webhooks = findById(id);
        Map<String, Object> response = new HashMap<>();

        if (webhooks == null) {
            throw new IllegalArgumentException("no se puede eliminar, el webhook con el ID: " + id + "No existe");
        } else {
            webhookRepository.deleteById(id);
        }
        response.put("mensaje", "Webhook eliminado con exito!!!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public Webhooks updateWebhook(Long id, Webhooks webhooks) {
        Webhooks webUpdate = findById(id);
        webhooks.setDate_create_webhook(new Date());
        webhooks.setId(id);
        BeanUtils.copyProperties(webhooks, webUpdate);
        return webhookRepository.save(webUpdate);
    }

}
