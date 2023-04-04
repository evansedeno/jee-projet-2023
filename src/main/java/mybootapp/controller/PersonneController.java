package mybootapp.controller;

import mybootapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/personne")
public class PersonneController {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @ModelAttribute("command")
    public Personne createPersonne() {
        return new Personne();
    }

    @RequestMapping("/test")
    public String test() {
        List<Personne> personnes = new ArrayList<>();
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        for (int i = 0; i < 1000; i++) {
            Personne personne = new Personne();
            personne.setNom("Nom " + i);
            personne.setPrenom("Prenom " + i);
            personne.setEmail("email" + i + "@gmail.com");
            personne.setSiteWeb("www.site" + i + ".com");
            personne.setDateDeNaissance(new Date());
            // On récupère un groupe aléatoire
            personne.setGroupe(groupes.get((int) (Math.random() * 100)));
            personne.setMotDePasse("motdepasse" + i);
            personnes.add(personne);
        }
        for (Personne personne : personnes) {
            directoryDAO.ajouterPersonne(personne);
        }
        return "liste";
    }

    @RequestMapping("/liste")
    public String listerPersonnes(Model model) {
        List<Personne> personnes = directoryDAO.rechercherToutesLesPersonnes();
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("personnes", personnes);
        model.addAttribute("groupes", groupes);
        return "personnes";
    }

    @RequestMapping("/{id}")
    public String afficherPersonne(@PathVariable("id") long id, Model model) {
        Personne personne = directoryDAO.rechercherPersonneParId(id);
        model.addAttribute("personne", personne);
        return "personne";
    }

    @RequestMapping("/recherche")
    public String rechercherPersonnes(
            @RequestParam(value = "identifiant", required = false) String id,
            @RequestParam(value = "nom", required = false) String nom,
            @RequestParam(value = "prenom", required = false) String prenom,
            @RequestParam(value = "siteWeb", required = false) String siteWeb,
            Model model) {
        List<Personne> personnes = new ArrayList<>();

        if (id != null && !id.isEmpty()) {
            long idPersonne = Long.parseLong(id);
            Personne personne = directoryDAO.rechercherPersonneParId(idPersonne);
            personnes.add(personne);
            model.addAttribute("personnes", personnes);
            return "recherche";
        }

        if (nom != null && !nom.isEmpty()) {
            personnes = directoryDAO.rechercherPersonnesParNom(nom);
            model.addAttribute("personnes", personnes);
        }

        if (prenom != null && !prenom.isEmpty()) {
            if (personnes.isEmpty()) {
                personnes = directoryDAO.rechercherPersonnesParPrenom(prenom);
            } else {
                personnes.retainAll(directoryDAO.rechercherPersonnesParPrenom(prenom));
            }
            model.addAttribute("personnes", personnes);
        }

        if (siteWeb != null && !siteWeb.isEmpty()) {
            if (personnes.isEmpty()) {
                personnes = directoryDAO.rechercherPersonnesParSiteWeb(siteWeb);
            } else {
                personnes.retainAll(directoryDAO.rechercherPersonnesParSiteWeb(siteWeb));
            }
            model.addAttribute("personnes", personnes);
        }

        return "recherche";
    }
}