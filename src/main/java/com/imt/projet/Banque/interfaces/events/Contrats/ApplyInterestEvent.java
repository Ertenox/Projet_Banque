package com.imt.projet.Banque.interfaces.events.Contrats;

import org.springframework.context.ApplicationEvent;

public class ApplyInterestEvent extends ApplicationEvent {

    public ApplyInterestEvent(Object source) {
        super(source);
    }
}
