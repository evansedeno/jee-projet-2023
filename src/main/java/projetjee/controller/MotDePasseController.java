package projetjee.controller;

import projetjee.model.IDirectoryDAO;
import projetjee.model.validators.motdepasse.JetonValidator;
import projetjee.model.validators.motdepasse.MotDePasseChangementValidator;
import projetjee.model.validators.motdepasse.MotDePasseOublieValidator;
import projetjee.model.objects.JetonMotDePasse;
import projetjee.model.objects.Personne;
import projetjee.model.objects.Utilisateur;
import projetjee.model.services.JetonMotDePasseService;
import projetjee.model.services.MotDePasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/motdepasse")
@Controller
public class MotDePasseController {

    @Autowired
    IDirectoryDAO directoryDAO;

    @Autowired
    MotDePasseOublieValidator motDePasseOublieValidator;

    @Autowired
    MotDePasseChangementValidator motDePasseChangementValidator;

    @Autowired
    JetonValidator jetonValidator;

    @Autowired
    Utilisateur utilisateur;

    @Autowired
    private JetonMotDePasseService jetonMotDePasseService;

    @GetMapping("/reinitialiser")
    public String motDePassOublieFormulaire(Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        model.addAttribute("personne", new Personne());
        return "reinitialiser-motdepasse";
    }

    @PostMapping("/reinitialiser")
    public String motDePassOublie(@ModelAttribute("personne") Personne personne, BindingResult result, Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        motDePasseOublieValidator.validate(personne, result);
        if (!result.hasErrors()) {
            Personne p = directoryDAO.rechercherPersonneParEmail(personne.getEmail());
            JetonMotDePasse jetonMotDePasse = jetonMotDePasseService.creerJeton(p);
            jetonMotDePasseService.envoyerEmail(jetonMotDePasse);
            model.addAttribute("emailEnvoie", true);
        }
        return "reinitialiser-motdepasse";
    }

    @GetMapping("/{token}")
    public String changementMotDePasseFormulaire(@PathVariable("token") String token, Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        JetonMotDePasse jetonMotDePasse = directoryDAO.rechercherJetonParToken(token);
        if (jetonMotDePasse == null || !jetonMotDePasse.estValide()) {
            return "redirect:/";
        }
        model.addAttribute("jetonMotDePasse", jetonMotDePasse);
        return "jeton-motdepasse";
    }

    @PostMapping("/{token}")
    public String changementMotDePasse(@PathVariable("token") String token, @ModelAttribute("jetonMotDePasse") JetonMotDePasse jetonMotDePasse, BindingResult result, Model model) {
        if (utilisateur.getPersonne() != null) {
            return "redirect:/";
        }
        if (!token.equals(jetonMotDePasse.getToken())) {
            return "redirect:/";
        }
        JetonMotDePasse j = directoryDAO.rechercherJetonParToken(token);
        if (j == null) {
            return "redirect:/";
        }
        j.setNouveauMotDePasse(jetonMotDePasse.getNouveauMotDePasse());
        j.setConfirmationMotDePasse(jetonMotDePasse.getConfirmationMotDePasse());
        jetonValidator.validate(j, result);
        if (!result.hasErrors()) {
            String p = MotDePasseService.crypterMotDePasse(j.getNouveauMotDePasse());
            j.getPersonne().setMotDePasse(p);
            directoryDAO.modifierPersonne(j.getPersonne());
            model.addAttribute("motDePasseModifie", true);
        }
        return "jeton-motdepasse";
    }
}