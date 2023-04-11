package projetjee.model.validators.motdepasse;

import projetjee.model.objects.JetonMotDePasse;
import projetjee.model.objects.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JetonValidatorTest {

    private JetonValidator jetonValidator;

    @BeforeEach
    public void setUp() {
        jetonValidator = new JetonValidator();
    }

    @Test
    public void testValidation() {
        JetonMotDePasse jetonMotDePasse = new JetonMotDePasse();
        jetonMotDePasse.setPersonne(new Personne());
        jetonMotDePasse.setNouveauMotDePasse("123456789");
        jetonMotDePasse.setConfirmationMotDePasse("123456789");

        Errors errors = new BeanPropertyBindingResult(jetonMotDePasse, "jetonMotDePasse");

        jetonValidator.validate(jetonMotDePasse, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void testValidationTokenInvalide() {
        JetonMotDePasse jetonMotDePasse = creerJetonMotDePasseValide();
        jetonMotDePasse.setToken("invalidToken");

        Errors errors = new BeanPropertyBindingResult(jetonMotDePasse, "jetonMotDePasse");

        jetonValidator.validate(jetonMotDePasse, errors);

        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("token"));
    }

    @Test
    public void testValidationPersonneInexistante() {
        JetonMotDePasse jetonMotDePasse = creerJetonMotDePasseValide();
        jetonMotDePasse.setPersonne(null);

        Errors errors = new BeanPropertyBindingResult(jetonMotDePasse, "jetonMotDePasse");

        jetonValidator.validate(jetonMotDePasse, errors);

        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("personne"));
    }

    @Test
    public void testValidationDateExpire() {
        JetonMotDePasse jetonMotDePasse = creerJetonMotDePasseValide();
        jetonMotDePasse.setDateDeExpiration(new Date(System.currentTimeMillis() - 10000));

        Errors errors = new BeanPropertyBindingResult(jetonMotDePasse, "jetonMotDePasse");

        jetonValidator.validate(jetonMotDePasse, errors);

        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("dateDeExpiration"));
    }

    // Add this helper method to create a valid JetonMotDePasse instance for testing
    private JetonMotDePasse creerJetonMotDePasseValide() {
        JetonMotDePasse jetonMotDePasse = new JetonMotDePasse();
        jetonMotDePasse.setToken("12345678901234567890123456789012");
        jetonMotDePasse.setPersonne(new Personne());
        jetonMotDePasse.setDateDeExpiration(new Date(System.currentTimeMillis() + 10000));
        jetonMotDePasse.setNouveauMotDePasse("NewP@ssw0rd");
        jetonMotDePasse.setConfirmationMotDePasse("NewP@ssw0rd");
        return jetonMotDePasse;
    }
}