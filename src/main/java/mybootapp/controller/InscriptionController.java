package mybootapp.controller;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Groupe;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.services.MotDePasseService;
import mybootapp.model.validators.personne.InscriptionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/inscription")
@Controller
public class InscriptionController {

    @Autowired
    IDirectoryDAO directoryDAO;

    @Autowired
    InscriptionValidator personneInscriptionValidator;

    @Autowired
    Utilisateur utilisateur;

    @GetMapping("")
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
        return "inscription-personne";
    }

    @PostMapping("")
    public String inscription(@ModelAttribute("personne") Personne personne, Model model, BindingResult result) {
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
        personneInscriptionValidator.validate(personne, result);
        if (result.hasErrors()) {
            return "inscription-personne";
        } else {
            String p = MotDePasseService.crypterMotDePasse(personne.getMotDePasse());
            personne.setMotDePasse(p);
            int groupe_id = Integer.parseInt(personne.getGroupe().getNom());
            Groupe groupe = directoryDAO.rechercherGroupeParId(groupe_id);
            personne.setGroupe(groupe);
            directoryDAO.ajouterPersonne(personne);
            utilisateur.setPersonne(personne);
            model.addAttribute("inscriptionReussie", true);
            return "redirect:/";
        }
    }
}