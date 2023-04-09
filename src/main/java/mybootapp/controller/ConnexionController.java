package mybootapp.controller;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Groupe;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.validators.PersonneConnexionValidator;
import mybootapp.model.validators.PersonneInscriptionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/connexion")
@Controller
public class ConnexionController {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Autowired
    PersonneConnexionValidator personneConnexionValidator;

    @Autowired
    Utilisateur utilisateur;

    @GetMapping("")
    public String connexionFormulaire(Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        model.addAttribute("personne", new Personne());
        return "connexion";

    }

    @PostMapping("")
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
}