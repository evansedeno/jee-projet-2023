package mybootapp.model.validators;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import mybootapp.model.objects.*;

@Component
public class RechercheValidator implements Validator {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Recherche recherche = (Recherche) target;

        // Validation du nom
        if (!recherche.getNom().isEmpty()) {
            if (recherche.getNom().length() < 2) {
                errors.rejectValue("nom", "recherche.nom.tropCourt", "Le nom est trop court.");
            }
            if (recherche.getNom().length() > 50) {
                errors.rejectValue("nom", "recherche.nom.tropLong", "Le nom est trop long.");
            }
            if (recherche.getNom().matches(".*\\d.*")) {
                errors.rejectValue("nom", "recherche.nom.chiffre", "Le nom ne doit pas contenir de chiffres.");
            }
            if (!recherche.getNom().matches("^[a-zA-Z]+$")) {
                errors.rejectValue("nom", "recherche.nom.caractereSpecial", "Le nom ne doit pas contenir de caractères spéciaux.");
            }
        }

        // Validation du prénom
        if (!recherche.getPrenom().isEmpty()) {
            if (recherche.getPrenom().length() < 2) {
                errors.rejectValue("prenom", "recherche.prenom.tropCourt", "Le prénom est trop court.");
            }
            if (recherche.getPrenom().length() > 50) {
                errors.rejectValue("prenom", "recherche.prenom.tropLong", "Le prénom est trop long.");
            }
            if (recherche.getPrenom().matches(".*\\d.*")) {
                errors.rejectValue("prenom", "recherche.prenom.chiffre", "Le prénom ne doit pas contenir de chiffres.");
            }
            if (!recherche.getPrenom().matches("^[a-zA-Z]+$")) {
                errors.rejectValue("prenom", "recherche.prenom.caractereSpecial", "Le prénom ne doit pas contenir de caractères spéciaux.");
            }
        }

        // Validation du site web
        if (!recherche.getSiteWeb().isEmpty()) {
            if (recherche.getSiteWeb().length() < 2) {
                errors.rejectValue("siteWeb", "recherche.siteWeb.tropCourt", "Le site web est trop court.");
            }
            if (recherche.getSiteWeb().length() > 50) {
                errors.rejectValue("siteWeb", "recherche.siteWeb.tropLong", "Le site web est trop long.");
            }
            if (!recherche.getSiteWeb().matches("^(http|https)://.*")) {
                errors.rejectValue("siteWeb", "recherche.siteWeb.format", "Le site web doit commencer par http:// ou https://.");
            }
        }

        // Validation du groupe
        if (!recherche.getGroupe().isEmpty()) {
            long id = Long.parseLong(recherche.getGroupe());
            Groupe groupe = directoryDAO.rechercherGroupeParId(id);
            if (groupe == null) {
                errors.rejectValue("groupe", "personne.groupe.existePas");
            }
        }
    }


}