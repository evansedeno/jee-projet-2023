package projetjee.model.validators.personne;

import projetjee.model.objects.Groupe;
import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class ModificationValidator implements Validator {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Personne personne = (Personne) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "nom.vide");
        if (personne.getNom().length() < 2) {
            errors.rejectValue("nom", "nom.tropCourt");
        }
        if (personne.getNom().length() > 50) {
            errors.rejectValue("nom", "nom.tropLong");
        }
        if (personne.getNom().matches(".*\\d.*")) {
            errors.rejectValue("nom", "nom.chiffre");
        }
        if (personne.getNom().matches(".*[^\\w\\s-_].*")) {
            errors.rejectValue("nom", "nom.caractereSpecial");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prenom", "prenom.vide");
        if (personne.getPrenom().length() < 2) {
            errors.rejectValue("prenom", "prenom.tropCourt");
        }
        if (personne.getPrenom().length() > 50) {
            errors.rejectValue("prenom", "prenom.tropLong");
        }
        if (personne.getPrenom().matches(".*\\d.*")) {
            errors.rejectValue("prenom", "prenom.chiffre");
        }
        if (personne.getPrenom().matches(".*[^\\w\\s-_].*")) {
            errors.rejectValue("prenom", "prenom.caractereSpecial");
        }

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

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupe", "personne.groupe.vide");
        if (personne.getGroupe() != null && personne.getGroupe().getId() > 0) {
            Groupe groupe = directoryDAO.rechercherGroupeParId(personne.getGroupe().getId());
            if (groupe == null) {
                errors.rejectValue("groupe", "personne.groupe.existePas");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateDeNaissance", "dateDeNaissance.vide");
        LocalDate dateDeNaissance = personne.getDateDeNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate today = LocalDate.now();
        if (dateDeNaissance.isAfter(today)) {
            errors.rejectValue("dateDeNaissance", "personne.dateDeNaissance.invalide");
        }
    }
}
