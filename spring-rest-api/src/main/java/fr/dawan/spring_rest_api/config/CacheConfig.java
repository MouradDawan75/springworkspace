package fr.dawan.spring_rest_api.config;

import fr.dawan.spring_rest_api.entities.Salarie;
import fr.dawan.spring_rest_api.services.CacheSalarieService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    /*
    Spring Boot utilise un stockage en mémoire qui est une map
    Possibilité d'initialiser le cache au démarrage de l'appli.
     */

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheSalarieService salarieService;

    @PostConstruct
    public void init(){
        System.out.println(">>> Initialisation du cache.......");
        Cache cache = cacheManager.getCache("initCache");
        List<Salarie> salaries = salarieService.getAll();

        for(Salarie s : salaries){
            cache.put(s.getId(), s);
        }
    }

}
