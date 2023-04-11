package mybootapp.model.validators;

import mybootapp.model.objects.Groupe;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class GroupeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Groupe.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Groupe groupe = (Groupe) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "groupe.nom.vide", "Le nom est obligatoire.");
        if (groupe.getNom().length() < 2) {
            errors.rejectValue("nom", "groupe.nom.tropCourt", "Le nom est trop court.");
        }
        if (groupe.getNom().length() > 50) {
            errors.rejectValue("nom", "groupe.nom.tropLong", "Le nom est trop long.");
        }
    }
}
