package mybootapp.controller;

import mybootapp.model.*;
import mybootapp.model.objects.*;
import mybootapp.model.services.MotDePasseService;
import mybootapp.model.validators.motdepasse.MotDePasseChangementValidator;
import mybootapp.model.validators.motdepasse.MotDePasseOublieValidator;
import mybootapp.model.validators.personne.ModificationValidator;
import mybootapp.model.validators.RechercheValidator;
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

    @Autowired
    Utilisateur utilisateur;

    @Autowired
    ModificationValidator personneModificationValidator;

    @Autowired
    MotDePasseChangementValidator motDePasseChangementValidator;

    @Autowired
    RechercheValidator rechercheValidator;

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
        return "personnes";
    }

    @RequestMapping("/liste")
    public String listerPersonnes(Model model) {
        List<Personne> personnes = directoryDAO.rechercherToutesLesPersonnes();
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("personnes", personnes);
        model.addAttribute("groupes", groupes);
        model.addAttribute("utilisateur", utilisateur);
        return "personnes";
    }

    @RequestMapping("/{id}")
    public String afficherPersonne(@PathVariable("id") long id, Model model) {
        Personne personne = directoryDAO.rechercherPersonneParId(id);
        if (personne == null) {
            return "redirect:/personne/liste";
        }
        model.addAttribute("personne", personne);
        model.addAttribute("utilisateur", utilisateur);
        return "personne";
    }

    @GetMapping("/{id}/modifier")
    public String modifierPersonne(@PathVariable("id") long id, Model model) {
        if (utilisateur.getPersonne() == null || utilisateur.getPersonne().getId() != id) {
            return "redirect:/";
        }
        Personne personne = directoryDAO.rechercherPersonneParId(id);
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("personne", personne);
        model.addAttribute("groupes", groupes);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("changementMotDePasse", new ChangementMotDePasse());
        return "modifier-personne";
    }

    @PostMapping("/{id}/modifier")
    public String modifierPersonne(@PathVariable("id") long id, @ModelAttribute("personne") Personne personne, BindingResult result, Model model) {
        if (utilisateur.getPersonne() == null || utilisateur.getPersonne().getId() != id) {
            return "redirect:/";
        }
        int groupe_id = Integer.parseInt(personne.getGroupe().getNom());
        Groupe groupe = directoryDAO.rechercherGroupeParId(groupe_id);
        personne.setGroupe(groupe);
        personneModificationValidator.validate(personne, result);
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("groupes", groupes);
        model.addAttribute("utilisateur", utilisateur);
        if (result.hasErrors()) {
            return "modifier-personne";
        }
        String p = MotDePasseService.crypterMotDePasse(personne.getMotDePasse());
        personne.setMotDePasse(p);
        directoryDAO.modifierPersonne(personne);
        utilisateur.setPersonne(personne);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("modificationReussie", true);
        personne = directoryDAO.rechercherPersonneParId(id);
        model.addAttribute("personne", personne);
        return "modifier-personne";
    }

    @PostMapping("/{id}/modifier/motdepasse")
    public String motDePasseChangement(@PathVariable("id") long id, @ModelAttribute("changementMotDePasse") ChangementMotDePasse changementMotDePasse, Model model, BindingResult result) {
        if (utilisateur.getPersonne() == null || utilisateur.getPersonne().getId() != id) {
            return "redirect:/";
        }
        changementMotDePasse.setPersonne(utilisateur.getPersonne());
        motDePasseChangementValidator.validate(changementMotDePasse, result);
        if (!result.hasErrors()) {
            String p = MotDePasseService.crypterMotDePasse(changementMotDePasse.getPersonne().getMotDePasse());
            changementMotDePasse.getPersonne().setMotDePasse(p);
            utilisateur.setPersonne(changementMotDePasse.getPersonne());
            model.addAttribute("motDePasseReussie", true);
        }
        model.addAttribute("utilisateur", utilisateur);
        Personne personne = directoryDAO.rechercherPersonneParId(id);
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("personne", personne);
        model.addAttribute("groupes", groupes);
        return "modifier-personne";
    }

    @PostMapping("/{id}/modifier/supprimer")
    public String supprimerPersonne(@PathVariable("id") long id) {
        if (utilisateur.getPersonne() == null || utilisateur.getPersonne().getId() != id) {
            return "redirect:/";
        }
        Personne personne = directoryDAO.rechercherPersonneParId(id);
        directoryDAO.supprimerPersonne(personne);
        utilisateur.setPersonne(null);
        return "redirect:/";
    }

    @GetMapping("/rechercher")
    public String rechercherPersonnes(Model model) {
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("groupes", groupes);
        model.addAttribute("utilisateur", utilisateur);
        return "rechercher";
    }

    @PostMapping("/rechercher")
    public String rechercherPersonnes(
            @ModelAttribute("recherche") Recherche recherche,
            BindingResult result,
            Model model) {
        rechercheValidator.validate(recherche, result);
        model.addAttribute("utilisateur", utilisateur);
        if (result.hasErrors()) {
            return "rechercher";
        }
        Recherche r = new Recherche(recherche.getId(),
                recherche.getNom(),
                recherche.getPrenom(),
                recherche.getSiteWeb(),
                recherche.getGroupe(),
                directoryDAO);
        List<Personne> lp = r.rechercherPersonnes();
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("groupes", groupes);
        model.addAttribute("personnes", lp);
        return "rechercher";
    }

}