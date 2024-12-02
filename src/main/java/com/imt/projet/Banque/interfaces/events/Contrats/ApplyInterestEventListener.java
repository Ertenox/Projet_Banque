package com.imt.projet.Banque.interfaces.events.Contrats;

import com.imt.projet.Banque.application.Metier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplyInterestEventListener {

    private final Metier metier;

    public ApplyInterestEventListener(Metier metier) {
        this.metier = metier;
    }

    @EventListener
    public void handleApplyInterestEvent(ApplyInterestEvent event) {
        System.out.println("Reception d'un event, application des interet...");
        metier.applyInterest();
    }
}
