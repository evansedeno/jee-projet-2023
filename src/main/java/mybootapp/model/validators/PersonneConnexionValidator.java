package mybootapp.model.validators;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonneConnexionValidator implements Validator {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Personne personne = (Personne) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.vide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "motDePasse", "motDePasse.vide");
        if (directoryDAO.authentifier(personne.getEmail(), personne.getMotDePasse()) == null) {
            errors.rejectValue("motDePasse", "motDePasse.incorrect");
        }
    }
}
