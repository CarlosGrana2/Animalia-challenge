package com.mercadolibre.animalia.repositories;

import com.mercadolibre.animalia.models.Webhooks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebhookRepository extends JpaRepository<Webhooks, Long> {
}
