package mybootapp.controller;

import mybootapp.model.Groupe;
import mybootapp.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        // On créer un miller de personnes et une centaine de groupes
        List<Personne> personnes = new ArrayList<>();
        List<Groupe> groupes = new ArrayList<>();

        // On créer 100 groupes avec des noms différents
        for (int i = 0; i < 100; i++) {
            Groupe groupe = new Groupe();
            groupe.setNom("Groupe " + i);
            groupes.add(groupe);
        }
        for (Groupe groupe : groupes) {
            directoryService.ajouterGroupe(groupe);
        }

        // On créer 1000 personnes avec des noms différents
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
            directoryService.ajouterPersonne(personne);
        }

        model.addAttribute("personnes", personnes);

        return "test";
    }

    @RequestMapping("/groupes")
    public String listerGroupes(Model model) {
        List<Groupe> groupes = directoryService.trouverTousLesGroupes();
        model.addAttribute("groupes", groupes);
        return "groupes";
    }

    @RequestMapping("/groupe")
    public String afficherGroupe(@RequestParam("id") long id, Model model) {
        Groupe groupe = directoryService.trouverGroupeParId(id);
        List<Personne> personnes = directoryService.trouverPersonnesParGroupe(groupe);
        model.addAttribute("groupe", groupe);
        model.addAttribute("personnes", personnes);
        return "groupe";
    }

    @RequestMapping("/personnes")
    public String listerPersonnes(Model model) {
        List<Personne> personnes = directoryService.trouverToutesLesPersonnes();
        List<Groupe> groupes = directoryService.trouverTousLesGroupes();
        model.addAttribute("personnes", personnes);
        model.addAttribute("groupes", groupes);
        return "personnes";
    }

    @RequestMapping("/personne")
    public String afficherPersonne(@RequestParam("id") long id, Model model) {
        Personne personne = directoryService.trouverPersonneParId(id);
        model.addAttribute("personne", personne);
        return "personne";
    }

    @ModelAttribute("command")
    public Personne createPersonne() {
        return new Personne();
    }

    @GetMapping("/ajouter-personne")
    public String afficherFormulaireAjoutPersonne(Model model) {
        List<Groupe> groupes = directoryService.trouverTousLesGroupes();
        model.addAttribute("groupes", groupes);
        model.addAttribute("personne", new Personne());
        return "ajouter-personne";
    }

    @PostMapping("/ajouter-personne")
    public String ajouterPersonne(@ModelAttribute("personne") Personne personne) {
        int groupe_id = Integer.parseInt(personne.getGroupe().getNom());
        Groupe groupe = directoryService.trouverGroupeParId(groupe_id);
        personne.setGroupe(groupe);
        directoryService.ajouterPersonne(personne);
        return "redirect:/personnes";
    }

    @ModelAttribute("command")
    public Groupe createGroupe() {
        return new Groupe();
    }

    @GetMapping("/ajouter-groupe")
    public String afficherFormulaireAjoutGroupe(Model model) {
        model.addAttribute("groupe", new Groupe());
        return "ajouter-groupe";
    }

    @PostMapping("/ajouter-groupe")
    public String ajouterGroupe(@ModelAttribute("groupe") Groupe groupe) {
        directoryService.ajouterGroupe(groupe);
        return "redirect:/groupes";
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
            Personne personne = directoryService.trouverPersonneParId(idPersonne);
            personnes.add(personne);
            model.addAttribute("personnes", personnes);
            return "recherche";
        }

        if (nom != null && !nom.isEmpty()) {
            personnes = directoryService.rechercherPersonnesParNom(nom);
            model.addAttribute("personnes", personnes);
        }

        if (prenom != null && !prenom.isEmpty()) {
            if (personnes.isEmpty()) {
                personnes = directoryService.rechercherPersonnesParPrenom(prenom);
            } else {
                personnes.retainAll(directoryService.rechercherPersonnesParPrenom(prenom));
            }
            model.addAttribute("personnes", personnes);
        }

        if (siteWeb != null && !siteWeb.isEmpty()) {
            if (personnes.isEmpty()) {
                personnes = directoryService.rechercherPersonnesParSiteWeb(siteWeb);
            } else {
                personnes.retainAll(directoryService.rechercherPersonnesParSiteWeb(siteWeb));
            }
            model.addAttribute("personnes", personnes);
        }

        return "recherche";
    }
}