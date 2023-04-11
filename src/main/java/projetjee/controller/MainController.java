package projetjee.controller;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Groupe;
import projetjee.model.objects.Personne;
import projetjee.model.objects.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projetjee.model.services.MotDePasseService;

import java.time.LocalDate;

@Controller
public class MainController {

    @Autowired
    Utilisateur utilisateur;

    @Autowired
    IDirectoryDAO directoryDAO;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("utilisateur", utilisateur);
        return "index";
    }

    @RequestMapping("test")
    public String test() {

        // Création d'un Groupe des Professeurs
        Groupe g = new Groupe();
        g.setNom("Groupe des Professeurs");
        directoryDAO.ajouterGroupe(g);

        // Création d'un Groupe des Etudiants
        Groupe g2 = new Groupe();
        g2.setNom("Groupe des Etudiants");
        directoryDAO.ajouterGroupe(g2);

        // Ajout de la Personne JeanLuc Massat
        Personne p = new Personne();
        p.setNom("Massat");
        p.setPrenom("Jean-Luc");
        p.setEmail("jean-luc.massat@univ-amu.fr");
        p.setMotDePasse(MotDePasseService.crypterMotDePasse("MettezNous20SVP"));
        p.setSiteWeb("https://jean-luc-massat.pedaweb.univ-amu.fr/ens/");
        LocalDate date = LocalDate.of(2020, 2, 20);
        p.setDateDeNaissance(java.sql.Date.valueOf(date));
        p.setGroupe(g);
        directoryDAO.ajouterPersonne(p);

        // Ajout de la Personne Evan Sedeno
        Personne p2 = new Personne();
        p2.setNom("Sedeno");
        p2.setPrenom("Evan");
        p2.setEmail("evan.sedeno@etu.univ-amu.fr");
        p2.setMotDePasse(MotDePasseService.crypterMotDePasse("MettezNous20SVP"));
        p2.setSiteWeb("https://fr.linkedin.com/in/evan-sedeno");
        LocalDate date2 = LocalDate.of(2001, 1, 29);
        p2.setDateDeNaissance(java.sql.Date.valueOf(date2));
        p2.setGroupe(g2);
        directoryDAO.ajouterPersonne(p2);

        // Ajout de la Personne Guillaume Risch
        Personne p3 = new Personne();
        p3.setNom("Risch");
        p3.setPrenom("Guillaume");
        p3.setEmail("guillaume.risch@etu.univ-amu.fr");
        p3.setMotDePasse(MotDePasseService.crypterMotDePasse("MettezNous20SVP"));
        p3.setSiteWeb("https://fr.linkedin.com/in/guillaume-risch-40a3631ab");
        LocalDate date3 = LocalDate.of(1999, 1, 30);
        p3.setDateDeNaissance(java.sql.Date.valueOf(date3));
        p3.setGroupe(g2);
        directoryDAO.ajouterPersonne(p3);

        return "redirect:/";
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
