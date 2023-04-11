package projetjee.model.validators.motdepasse;

import projetjee.model.objects.JetonMotDePasse;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.Objects;

@Component
public class JetonValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        JetonMotDePasse jetonMotDePasse = (JetonMotDePasse) target;

        if (jetonMotDePasse.getToken().length() != 32) {
            errors.rejectValue("token", "token.incorrect");
        }
        if (jetonMotDePasse.getPersonne() == null) {
            errors.rejectValue("personne", "personne.nonExistant");
        }
        if (jetonMotDePasse.getDateDeExpiration().before(new Date())) {
            errors.rejectValue("dateDeExpiration", "dateDeExpiration.expiree");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nouveauMotDePasse", "nouveauMotDePasse.vide");
        if (jetonMotDePasse.getNouveauMotDePasse().length() < 8) {
            errors.rejectValue("nouveauMotDePasse", "nouveauMotDePasse.tropCourt");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationMotDePasse", "confirmationMotDePasse.vide");
        if (!Objects.equals(jetonMotDePasse.getNouveauMotDePasse(), jetonMotDePasse.getConfirmationMotDePasse())) {
            errors.rejectValue("confirmationMotDePasse", "confirmationMotDePasse.correspondPas");
        }
    }
}
