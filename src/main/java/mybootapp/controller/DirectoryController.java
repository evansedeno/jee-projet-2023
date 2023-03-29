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

        // Créez une nouvelle instance de Groupe
        Groupe groupe1 = new Groupe();
        groupe1.setNom("Groupe1");

        directoryService.ajouterGroupe(groupe1);

        // Créez une nouvelle instance de Person
        Personne personne1 = new Personne();
        personne1.setNom("Dupont");
        personne1.setPrenom("Pierre");
        personne1.setEmail("pierre.dupont@example.com");
        personne1.setDateDeNaissance(new Date());
        personne1.setSiteWeb("https://www.example.com");
        personne1.setMotDePasse("1234");
        personne1.setGroupe(groupe1);

        // Ajoutez la personne en utilisant la méthode save() de l'objet DirectoryDAO
        directoryService.ajouterPersonne(personne1);


        // Créez une nouvelle instance de Person
        Personne personne2 = new Personne();
        personne2.setNom("Durand");
        personne2.setPrenom("Paul");
        personne2.setEmail("paul.durand@example.com");
        personne2.setDateDeNaissance(new Date());
        personne2.setSiteWeb("https://www.example2.com");
        personne2.setMotDePasse("5678");
        personne2.setGroupe(groupe1);

        directoryService.ajouterPersonne(personne2);

        return "index";
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