package fr.dawan.spring_rest_api.controllers;

import fr.dawan.spring_rest_api.entities.Salarie;
import fr.dawan.spring_rest_api.services.CacheSalarieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalarieController {

    @Autowired
    private CacheSalarieService salarieService;

    @GetMapping
    public List<Salarie> getAll(){
        return salarieService.getAll();
    }

    @GetMapping(value = "/{id}")
    public Salarie getAById(@PathVariable("id") long id){
        return salarieService.getById(id);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") long id){
        salarieService.deleteById(id);
        return "salarié deleted.";
    }

    @PostMapping(value = "/save")
    public Salarie save(@RequestBody Salarie s){
        return salarieService.save(s);
    }

    @PutMapping(value = "/update")
    public Salarie update(@RequestBody Salarie s){
        return salarieService.update(s);
    }

    @GetMapping(value = "/clear-cache")
    public String clearCache(){
        return salarieService.clearAllCache();
    }

    @GetMapping(value = "/cache/delete/{id}")
    public String deleteSalarieCache(@PathVariable("id") long id){
        return salarieService.deleteSalarieFromCache(id);
    }
}
