package mybootapp.controller;

import mybootapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groupe")
public class GroupeController {

    @Autowired
    private IDirectoryDAO directoryDAO;

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
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        model.addAttribute("groupes", groupes);
        return "liste";
    }

    @RequestMapping("/{id}")
    public String afficherGroupe(@PathVariable("id") long id, Model model) {
        if (id < 0) {
            return "redirect:/liste";
        }
        Groupe groupe = directoryDAO.rechercherGroupeParId(id);
        List<Personne> personnes = directoryDAO.rechercherPersonnesParGroupe(groupe);
        model.addAttribute("groupe", groupe);
        model.addAttribute("personnes", personnes);
        return "groupe";
    }

    @GetMapping("/ajouter-groupe")
    public String afficherFormulaireAjoutGroupe(Model model) {
        model.addAttribute("groupe", new Groupe());
        return "ajouter-groupes";
    }

    @PostMapping("/ajouter-groupe")
    public String ajouterGroupe(@ModelAttribute("groupe") Groupe groupe) {
        directoryDAO.ajouterGroupe(groupe);
        return "redirect:/groupes";
    }

    @RequestMapping("/supprimer/{id}")
    public String supprimerGroupe(@PathVariable("id") long id) {
        Groupe groupe = directoryDAO.rechercherGroupeParId(id);
        directoryDAO.supprimerGroupe(groupe);
        return "redirect:/groupes";
    }

    @RequestMapping("/modifier/{id}")
public String modifierGroupe(@PathVariable("id") long id, Model model) {
        Groupe groupe = directoryDAO.rechercherGroupeParId(id);
        model.addAttribute("groupe", groupe);
        return "modifier-groupe";
    }

    @PostMapping("/modifier/{id}")
    public String modifierGroupe(@PathVariable("id") long id, @ModelAttribute("groupe") Groupe groupe) {
        directoryDAO.modifierGroupe(groupe);
        return "redirect:/groupes";
    }

    @RequestMapping("/rechercher")
    public String rechercherGroupe(@RequestParam("nom") String nom, Model model) {
        List<Groupe> groupes = directoryDAO.rechercherGroupeParNom(nom);
        model.addAttribute("groupes", groupes);
        return "groupes";
    }
}