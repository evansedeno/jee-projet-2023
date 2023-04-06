package mybootapp.controller;

import mybootapp.model.*;
import mybootapp.model.validators.GroupeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groupe")
public class GroupeController {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Autowired
    GroupeValidator groupeValidator;

    @Autowired
    Utilisateur utilisateur;

    @ModelAttribute("command")
    public Groupe createGroupe() {
        return new Groupe();
    }

    @RequestMapping("/test")
    public String test() {
        List<Groupe> groupes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Groupe groupe = new Groupe();
            groupe.setNom("Groupe " + i);
            groupes.add(groupe);
        }
        for (Groupe groupe : groupes) {
            directoryDAO.ajouterGroupe(groupe);
        }
        return "/test";
    }

    @RequestMapping("/liste")
    public String listerGroupes(Model model) {
        if (utilisateur.getPersonne() == null) {
            return "redirect:/";
        }
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("groupes", groupes);
        model.addAttribute("utilisateur", utilisateur);
        return "groupes";
    }

    @RequestMapping("/{id}")
    public String afficherGroupe(@PathVariable("id") long id, Model model) {
        if (utilisateur.getPersonne() == null) {
            return "redirect:/";
        }
        if (id < 0) {
            return "redirect:/liste";
        }
        Groupe groupe = directoryDAO.rechercherGroupeParId(id);
        List<Personne> personnes = directoryDAO.rechercherPersonnesParGroupe(groupe);
        model.addAttribute("groupe", groupe);
        model.addAttribute("personnes", personnes);
        model.addAttribute("utilisateur", utilisateur);
        return "groupe";
    }

    @GetMapping("/ajouter")
    public String afficherFormulaireAjoutGroupe(Model model) {
        if (utilisateur.getPersonne() == null) {
            return "redirect:/";
        }
        model.addAttribute("groupe", new Groupe());
        model.addAttribute("utilisateur", utilisateur);
        return "ajouter-groupe";
    }

    @PostMapping("/ajouter")
    public String ajouterGroupe(@ModelAttribute("groupe") Groupe groupe, BindingResult result, Model model) {
        if (utilisateur.getPersonne() == null) {
            return "redirect:/";
        }
        groupeValidator.validate(groupe, result);
        if (result.hasErrors()) {
            model.addAttribute("utilisateur", utilisateur);
            return "ajouter-groupe";
        } else {
            directoryDAO.ajouterGroupe(groupe);
            model.addAttribute("utilisateur", utilisateur);
            return "redirect:liste";
        }
    }

    @RequestMapping("/rechercher")
    public String rechercherGroupe(@RequestParam(value = "identifiant", required = false) String id,
                                   @RequestParam(value = "nom", required = false) String nom,
                                   Model model) {
        if (utilisateur.getPersonne() == null) {
            return "redirect:/";
        }
        List<Groupe> groupes = new ArrayList<>();
        Recherche recherche = new Recherche();
        recherche.rechercherGroupes(id, nom);
        model.addAttribute("groupes", groupes);
        model.addAttribute("utilisateur", utilisateur);
        return "rechercher";
    }
}