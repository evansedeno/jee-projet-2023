package mybootapp.controller;

import mybootapp.model.*;
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
    private IDirectoryDAO directoryDAO;

    @Autowired
    PersonneInscriptionValidator personneInscriptionValidator;

    @Autowired
    PersonneConnexionValidator personneConnexionValidator;

    @Autowired
    Utilisateur utilisateur;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("utilisateur", utilisateur);
        return "index";
    }

    @GetMapping("/connexion")
    public String connexionFormulaire(Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        model.addAttribute("personne", new Personne());
        return "connexion";

    }

    @PostMapping("/connexion")
    public String connexion(@ModelAttribute("personne") Personne personne, BindingResult result) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        personneConnexionValidator.validate(personne, result);
        if (result.hasErrors()) {
            return "connexion";
        } else {
            Personne utilisateurValide = directoryDAO.authentifier(personne.getEmail(), personne.getMotDePasse());
            utilisateur.setPersonne(utilisateurValide);
            return "redirect:/";
        }

    }

    @GetMapping("/inscription")
    public String inscriptionFormulaire(Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        if (groupes.isEmpty()) {
            Groupe groupe = new Groupe();
            groupe.setNom("Groupe par défaut");
            directoryDAO.ajouterGroupe(groupe);
            groupes.add(groupe);
        }
        model.addAttribute("groupes", groupes);
        model.addAttribute("personne", new Personne());
        model.addAttribute("utilisateur", utilisateur);
        return "inscription";
    }

    @PostMapping("/inscription")
    public String inscription(@ModelAttribute("personne") Personne personne, BindingResult result) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        personneInscriptionValidator.validate(personne, result);
        if (result.hasErrors()) {
            return "inscription";
        } else {
            int groupe_id = Integer.parseInt(personne.getGroupe().getNom());
            Groupe groupe = directoryDAO.rechercherGroupeParId(groupe_id);
            personne.setGroupe(groupe);
            directoryDAO.ajouterPersonne(personne);
            utilisateur.setPersonne(personne);
            return "redirect:/";
        }
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