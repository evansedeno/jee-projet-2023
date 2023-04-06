package mybootapp.model.validators;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

@Component
public class PersonneModificationMotDePasseValidator implements Validator {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Override
    public boolean supports(Class<?> clazz) {
        return Personne.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<String> motsDePasses = (List<String>) target;
        String motDePasse = motsDePasses.get(0);
        String nouveauMotDePasse = motsDePasses.get(1);
        String confirmationMotDePasse = motsDePasses.get(2);
        int id = Integer.parseInt(motsDePasses.get(3));
        Personne personne = directoryDAO.rechercherPersonneParId(id);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "motDePasse", "motDePasse.vide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nouveauMotDePasse", "nouveauMotDePasse.vide");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmationMotDePasse", "confirmationMotDePasse.vide");
        if (!Objects.equals(personne.getMotDePasse(), motDePasse)) {
            errors.rejectValue("motDePasse", "motDePasse.correspondPas");
        }
        if (!Objects.equals(nouveauMotDePasse, confirmationMotDePasse)) {
            errors.rejectValue("confirmationMotDePasse", "confirmationMotDePasse.correspondPas");
        }
        if (nouveauMotDePasse.length() < 8) {
            errors.rejectValue("nouveauMotDePasse", "nouveauMotDePasse.tropCourt");
        }
    }
}
