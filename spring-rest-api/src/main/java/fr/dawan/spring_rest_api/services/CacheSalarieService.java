package fr.dawan.spring_rest_api.services;

import fr.dawan.spring_rest_api.entities.Salarie;
import fr.dawan.spring_rest_api.repositories.SalarieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheSalarieService {

    @Autowired
    private SalarieRepository salarieRepository;

    //@Cacheable("initCache")
    public List<Salarie> getAll(){
        return salarieRepository.findAll();
    }

    @Cacheable(value = "initCache", key = "#id")
    public Salarie getById(long id){
        return salarieRepository.findById(id).get();
    }

    public Salarie save(Salarie s){
        return salarieRepository.saveAndFlush(s);
    }

    /*
    la clé du CachePut doit matcher avec un des params de la méthode
     */
    @CachePut(value = "initCache", key = "#s.id")
    public Salarie update(Salarie s){
        return salarieRepository.saveAndFlush(s);
    }

    public void deleteById(long id){
        salarieRepository.deleteById(id);
    }

    //Vider le contenu du cache
    @CacheEvict(value = "initCache", allEntries = true)
    public String clearAllCache(){
        System.out.println("Clear du cache");
        return "cache cleared";
    }

    //Supprimer un objet dans le cache
    @CacheEvict(value = "initCache", key = "#id")
    public String deleteSalarieFromCache(long id){
        System.out.println("Suppression d'un salarié du cache");
        return "Salarié id = "+id+" supprimé du cache.";
    }
}
