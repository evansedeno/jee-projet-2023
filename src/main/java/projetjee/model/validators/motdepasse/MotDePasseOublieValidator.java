package projetjee.model.validators.motdepasse;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.JetonMotDePasse;
import projetjee.model.objects.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

@Component
public class MotDePasseOublieValidator implements Validator {

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
        if (personne.getEmail().length() < 5) {
            errors.rejectValue("email", "email.tropCourt");
        }
        if (personne.getEmail().length() > 50) {
            errors.rejectValue("email", "email.tropLong");
        }
        List<Personne> personnes = directoryDAO.rechercherToutesLesPersonnes();
        if (personnes.isEmpty()) {
            errors.rejectValue("email", "email.existePas");
        } else {
            for (Personne p : personnes) {
                if (Objects.equals(p.getEmail(), personne.getEmail())) {
                    return;
                }
            }
            errors.rejectValue("email", "email.existePas");
        }
        for (Personne ignored : personnes) {
            List<JetonMotDePasse> jetons = directoryDAO.rechercherTousLesJetons();
            if (!errors.hasErrors()) {
                for (JetonMotDePasse j : jetons) {
                    if (Objects.equals(j.getPersonne().getEmail(), personne.getEmail())) {
                        directoryDAO.supprimerJeton(j);
                        break;
                    }
                }
            }
        }
    }
}
