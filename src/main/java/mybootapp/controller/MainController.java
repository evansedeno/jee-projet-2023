package mybootapp.controller;

import mybootapp.model.objects.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    public void setUtilisateur(Utilisateur utilisateurMock) {
        this.utilisateur = utilisateurMock;
    }
}
