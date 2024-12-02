package com.imt.projet.Banque.infrastructure.events.Contrats;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.imt.projet.Banque.interfaces.events.Contrats.ApplyInterestEvent;

@Component
public class ApplyInterestEventPublisher {

    private static final int TIME = 15*1000; // Dans ce cas, toutes les 15 secondes
    private final ApplicationEventPublisher eventPublisher;

    public ApplyInterestEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = TIME )
    public void publishApplyInterestEvent() {
        System.out.println("Publication d'un event...");
        eventPublisher.publishEvent(new ApplyInterestEvent(this));
    }
}
