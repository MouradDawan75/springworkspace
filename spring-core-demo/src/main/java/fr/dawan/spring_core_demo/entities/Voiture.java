package fr.dawan.spring_core_demo.entities;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Voiture {

    @Autowired
    private Moteur moteur;

    public Voiture(){
        System.out.println("constructeur.........");
    }

    public void demarrer(){
        moteur.demarrer();
    }

    @PostConstruct
    public void apresConstruction(){
        System.out.println("post construction.......");
    }

    @PreDestroy
    public void avantDestruction(){
        /*
        Sert dans la pratique à libérer les ressources utilisées
         */
        System.out.println("avant destruction......");
    }

}
