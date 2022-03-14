package com.mercadolibre.animalia.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "webhooks")
public class Webhooks {
    @Id
    @Column(name = "webhooks_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String organization;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NonNull
    private String url_notification;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_create_webhook;

}
