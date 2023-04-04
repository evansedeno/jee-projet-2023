package mybootapp.controller;

import mybootapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Autowired
    PersonneValidator personneValidator;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/connexion")
    public String connexionFormulaire() {
        return "connexion";
    }

    @PostMapping("/connexion")
    public String connexion(@RequestParam("email") String email, @RequestParam("motDePasse") String motDePasse, BindingResult result) {
        // On envoie les données PersonneValidator
        Personne personne = new Personne();
        personne.setEmail(email);
        personne.setMotDePasse(motDePasse);
        personneValidator.validate(personne, null);
        if (result.hasErrors()) {
            return "connexion";
        } else {
            boolean valider = directoryDAO.authentifier(email, motDePasse);
            return valider ? "redirect:/" : "connexion";
        }
    }

    @GetMapping("/inscription")
    public String inscriptionFormulaire(Model model) {
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("groupes", groupes);
        model.addAttribute("personne", new Personne());
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscription(@ModelAttribute("personne") Personne personne, BindingResult result) {
        personneValidator.validate(personne, result);
        if (result.hasErrors()) {
            return "inscription";
        } else {
            int groupe_id = Integer.parseInt(personne.getGroupe().getNom());
            Groupe groupe = directoryDAO.rechercherGroupeParId(groupe_id);
            personne.setGroupe(groupe);
            directoryDAO.ajouterPersonne(personne);
            return "redirect:/";
        }
    }
}