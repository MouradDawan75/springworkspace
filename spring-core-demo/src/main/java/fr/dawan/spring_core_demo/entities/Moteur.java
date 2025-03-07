package fr.dawan.spring_core_demo.entities;

import org.springframework.stereotype.Component;

@Component
public class Moteur {

    public void demarrer(){
        System.out.println("Moteur démarré......");
    }
}
