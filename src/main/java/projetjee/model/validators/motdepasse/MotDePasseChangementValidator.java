package projetjee.model.validators.motdepasse;

import projetjee.model.objects.ChangementMotDePasse;
import projetjee.model.objects.Personne;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import projetjee.model.services.MotDePasseService;

import java.util.Objects;

@Component
public class MotDePasseChangementValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangementMotDePasse changementMotDePasse = (ChangementMotDePasse) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ancienMotDePasse", "ancienMotDePasse.vide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nouveauMotDePasse", "nouveauMotDePasse.vide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationMotDePasse", "confirmationMotDePasse.vide");
        if (!MotDePasseService.comparerMotDePasse(changementMotDePasse.getAncienMotDePasse(), changementMotDePasse.getPersonne().getMotDePasse())) {
            errors.rejectValue("ancienMotDePasse", "ancienMotDePasse.correspondPas");
        }
        if (!Objects.equals(changementMotDePasse.getNouveauMotDePasse(), changementMotDePasse.getConfirmationMotDePasse())) {
            errors.rejectValue("confirmationMotDePasse", "confirmationMotDePasse.correspondPas");
        }
        if (changementMotDePasse.getNouveauMotDePasse().length() < 8) {
            errors.rejectValue("nouveauMotDePasse", "nouveauMotDePasse.tropCourt");
        }

    }
}
