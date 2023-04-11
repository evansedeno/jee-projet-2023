package projetjee.model.validators;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import projetjee.model.objects.*;

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

        if (!recherche.getNom().isEmpty()) {
            if (recherche.getNom().matches(".*\\d.*")) {
                errors.rejectValue("nom", "recherche.nom.chiffre", "Le nom ne doit pas contenir de chiffres.");
            }
            if (!recherche.getNom().matches("^[a-zA-Z]+$")) {
                errors.rejectValue("nom", "recherche.nom.caractereSpecial", "Le nom ne doit pas contenir de caractères spéciaux.");
            }
        }

        if (!recherche.getPrenom().isEmpty()) {
            if (recherche.getPrenom().matches(".*\\d.*")) {
                errors.rejectValue("prenom", "recherche.prenom.chiffre", "Le prénom ne doit pas contenir de chiffres.");
            }
            if (!recherche.getPrenom().matches("^[a-zA-Z]+$")) {
                errors.rejectValue("prenom", "recherche.prenom.caractereSpecial", "Le prénom ne doit pas contenir de caractères spéciaux.");
            }
        }

        if (!recherche.getSiteWeb().isEmpty()) {
            if (!recherche.getSiteWeb().matches("^(http|https)://.*")) {
                errors.rejectValue("siteWeb", "recherche.siteWeb.format", "Le site web doit commencer par http:// ou https://.");
            }
        }

        if (!recherche.getGroupe().isEmpty()) {
            long id = Long.parseLong(recherche.getGroupe());
            Groupe groupe = directoryDAO.rechercherGroupeParId(id);
            if (groupe == null) {
                errors.rejectValue("groupe", "personne.groupe.existePas");
            }
        }
    }


}
