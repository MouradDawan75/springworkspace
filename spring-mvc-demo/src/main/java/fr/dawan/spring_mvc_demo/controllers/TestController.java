package fr.dawan.spring_mvc_demo.controllers;

import fr.dawan.spring_mvc_demo.formbeans.PlayerForm;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/*
Pour DevTool:
Project Setting -> Build -> compiler -> Build Project Automaticaly ensuite -> advanced settings -> allow auto make to start......
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Player{
    private String name;
    private String role;

}

@Controller
public class TestController {

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model){

        Player player = new Player("Patrice", "rh");
        model.addAttribute("player", player);

        List<Player> players = new ArrayList<>();
        players.add(new Player("Thomas", "admin"));
        players.add(new Player("Marie", "manager"));

        model.addAttribute("players", players);

        //Liste pour le dropdown
        List<String> options = new ArrayList<>();

        options.add("option1");
        options.add("option2");
        options.add("option3");

        model.addAttribute("options", options);

        return "thymeleaf";
    }

    @PostMapping("/thymeleaf")
    public String postThymeleaf(@RequestParam("nom") String nom, @RequestParam("role") String role, Model model) {
        model.addAttribute("nom", nom);
        model.addAttribute("role", role);
        model.addAttribute("formOk", true);

        //return "thymeleaf"; renvoyer la page
        //return "redirect:/thymeleaf"; redirection vers le lien
        return thymeleaf(model);
    }

    @PostMapping("/formValidation")
    public String formValidation(@Valid @ModelAttribute("playerForm")PlayerForm pForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            // form invalide


            return thymeleaf(model);
        }

        model.addAttribute("formValide", true);

        return thymeleaf(model);
    }

    //Request Params

    @GetMapping("/thymeleaf/redirect")
    public String redirect(){

        return "redirect:/thymeleaf?q=thymeleaf&name=mvc";
    }

    @GetMapping("/thymeleaf/session")
    public String sessionParam(HttpSession session){
        session.setAttribute("nom", "dawan");

        return "redirect:/thymeleaf";
    }

    @GetMapping("/thymeleaf/servletContext")
    public String contextParam(){

        servletContext.setAttribute("prenom","paris");

        return "redirect:/thymeleaf";
    }


    /*
    @ModelAttribute: permet d'insérer un objet dans le model a chaque fois qu'1 méthode
    du controller est appelée
    model.addAttribute("playerForm", new PlayerForm());
     */
    @ModelAttribute("playerForm")
    public PlayerForm getPlayer(){
        return new PlayerForm();
    }
}
