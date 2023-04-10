package mybootapp.model.validators.motdepasse;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.ChangementMotDePasse;
import mybootapp.model.objects.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
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
        if (!Objects.equals(changementMotDePasse.getPersonne().getMotDePasse(), changementMotDePasse.getAncienMotDePasse())) {
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
