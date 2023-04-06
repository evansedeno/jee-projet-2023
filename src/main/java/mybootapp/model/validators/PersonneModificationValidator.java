package mybootapp.model.validators;

import mybootapp.model.Groupe;
import mybootapp.model.IDirectoryDAO;
import mybootapp.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class PersonneModificationValidator implements Validator {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Personne personne = (Personne) target;

        // Validation du nom
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "nom.vide");
        if (personne.getNom().length() < 2) {
            errors.rejectValue("nom", "nom.tropCourt");
        }
        if (personne.getNom().length() > 50) {
            errors.rejectValue("nom", "nom.tropLong");
        }
        if (personne.getNom().contains(" ")) {
            errors.rejectValue("nom", "nom.espace");
        }
        if (personne.getNom().matches(".*\\d.*")) {
            errors.rejectValue("nom", "nom.chiffre");
        }
        if (personne.getNom().matches(".*\\W.*")) {
            errors.rejectValue("nom", "nom.caractereSpecial");
        }

        // Validation du prénom
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenom", "prenom.vide");
        if (personne.getPrenom().length() < 2) {
            errors.rejectValue("prenom", "prenom.tropCourt");
        }
        if (personne.getPrenom().length() > 50) {
            errors.rejectValue("prenom", "prenom.tropLong");
        }
        if (personne.getPrenom().contains(" ")) {
            errors.rejectValue("prenom", "prenom.espace");
        }
        if (personne.getPrenom().matches(".*\\d.*")) {
            errors.rejectValue("prenom", "prenom.chiffre");
        }
        if (personne.getPrenom().matches(".*\\W.*")) {
            errors.rejectValue("prenom", "prenom.caractereSpecial");
        }

        // Validation du site web
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteWeb", "siteWeb.vide");

        if (personne.getSiteWeb().length() < 5) {
            errors.rejectValue("siteWeb", "siteWeb.tropCourt");
        }
        if (personne.getSiteWeb().length() > 50) {
            errors.rejectValue("siteWeb", "siteWeb.tropLong");
        }
        if (personne.getSiteWeb().contains(" ")) {
            errors.rejectValue("siteWeb", "siteWeb.espace");
        }
        if (!personne.getSiteWeb().matches("^(http|https)://.*")) {
            errors.rejectValue("siteWeb", "siteWeb.format");
        }

        // Validation de l'email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.vide");
        if (personne.getEmail().length() < 2) {
            errors.rejectValue("email", "email.tropCourt");
        }
        if (personne.getEmail().length() > 50) {
            errors.rejectValue("email", "email.tropLong");
        }
        if (!personne.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.rejectValue("email", "email.format");
        }

        // Validation du groupe
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupe", "personne.groupe.vide");
        if (personne.getGroupe() != null && personne.getGroupe().getId() > 0) {
            Groupe groupe = directoryDAO.rechercherGroupeParId(personne.getGroupe().getId());
            if (groupe == null) {
                errors.rejectValue("groupe", "personne.groupe.existePas");
            }
        }

        // Validation de la date de naissance
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateDeNaissance", "dateDeNaissance.vide");
        LocalDate dateDeNaissance = personne.getDateDeNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        if (dateDeNaissance.isAfter(today)) {
            errors.rejectValue("dateDeNaissance", "personne.dateDeNaissance.invalide");
        }
    }
}
