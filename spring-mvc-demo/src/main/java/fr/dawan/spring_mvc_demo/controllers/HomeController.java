package fr.dawan.spring_mvc_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"", "/"})
    public String accueil(Model model){
        model.addAttribute("Message", "Page d'accueil");
        return "index";
    }
}
