package projetjee.controller;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Personne;
import projetjee.model.objects.Utilisateur;
import projetjee.model.validators.personne.ConnexionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/connexion")
@Controller
public class ConnexionController {

    @Autowired
    public
    IDirectoryDAO directoryDAO;

    @Autowired
    public
    ConnexionValidator personneConnexionValidator;

    @Autowired
    public
    Utilisateur utilisateur;

    @GetMapping("")
    public String connexionFormulaire(Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        model.addAttribute("personne", new Personne());
        return "connexion-personne";
    }

    @PostMapping("")
    public String connexion(@ModelAttribute("personne") Personne personne, Model model, BindingResult result) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        personneConnexionValidator.validate(personne, result);
        if (result.hasErrors()) {
            return "connexion-personne";
        } else {
            Personne utilisateurValide = directoryDAO.authentifier(personne.getEmail(), personne.getMotDePasse());
            utilisateur.setPersonne(utilisateurValide);
            model.addAttribute("connexionReussie", true);
            return "redirect:/";
        }
    }
}