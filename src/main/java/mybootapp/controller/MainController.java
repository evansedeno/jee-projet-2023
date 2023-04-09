package mybootapp.controller;

import mybootapp.model.*;
import mybootapp.model.objects.Groupe;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.validators.PersonneConnexionValidator;
import mybootapp.model.validators.PersonneInscriptionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    Utilisateur utilisateur;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("utilisateur", utilisateur);
        return "index";
    }

    @RequestMapping("/deconnexion")
    public String deconnexion() {
        if (utilisateur.getPersonne() == null) {
            return "redirect:/";
        }
        utilisateur.setPersonne(null);
        return "redirect:/";
    }
}