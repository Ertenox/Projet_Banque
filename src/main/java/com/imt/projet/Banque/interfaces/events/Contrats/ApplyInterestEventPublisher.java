package com.imt.projet.Banque.interfaces.events.Contrats;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApplyInterestEventPublisher {

    private static final int TIME = 60000/4; 
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
