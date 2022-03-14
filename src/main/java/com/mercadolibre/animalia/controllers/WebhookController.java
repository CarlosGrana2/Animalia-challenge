package com.mercadolibre.animalia.controllers;

import com.mercadolibre.animalia.exceptions.InvalidDataException;
import com.mercadolibre.animalia.models.Citizen;
import com.mercadolibre.animalia.models.Webhooks;
import com.mercadolibre.animalia.services.WebhookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "WebHook rest controller", description = "REST API para administración de organizaciones a notificar")
@RequestMapping(value = "/api/webhook")
@RestController
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookServices webhookServices;

    @ApiOperation(value = "Crear nueva configuración de webhook para organización", response = Iterable.class, tags = "Api Rest Webhook")
    @PostMapping("/createwebhook")
    public Webhooks saveWebhook(@Valid @RequestBody Webhooks request, BindingResult result){
        if (result.hasErrors()){
            throw new InvalidDataException(result);
        }
        return webhookServices.createUrlNotification(request);
    }
    @ApiOperation(value = "Obtener todas las configuraciones de webhook para organización", response = Iterable.class, tags = "Api Rest Webhook")
    @GetMapping("/getallwebhook")
    public ResponseEntity getWebHook(){
        return ResponseEntity.ok(webhookServices.getAllWebhook());
    }

    @ApiOperation(value = "Obtener configuración de webhook por Id", response = Iterable.class, tags = "Api Rest Webhook")
    @GetMapping("/{id}")
    public Webhooks getWebhookById(@PathVariable("id") long id){
        return webhookServices.findById(id);
    }

    @ApiOperation(value = "Eliminar configuración de organización", response = Iterable.class, tags = "Api Rest Webhook")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRol(@PathVariable("id") long id) {
        return webhookServices.delete(id);
    }

    @ApiOperation(value = "Actualizar configuración de organización", response = Iterable.class, tags = "Api Rest Webhook")
    @PutMapping("/update/{id}")
    public Webhooks updateRol(@PathVariable("id") long id, @Valid @RequestBody Webhooks request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InvalidDataException(result);
        }
        return webhookServices.updateWebhook(id, request);
    }


    //Method for test the webhook the citizens
    @ApiOperation(value = "Servicio de ejemplo para consumir peticiones al webhook", response = Iterable.class, tags = "Api Rest Webhook")
    @PostMapping("/recivepost")
    public ResponseEntity getWebHookRecive(@RequestBody Citizen request){
        return ResponseEntity.ok(request);
    }



}
