package mybootapp.web;
import mybootapp.model.IDirectoryDAO;
import mybootapp.model.Personne;
import mybootapp.model.validators.PersonneConnexionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class PersonneConnexionValidatorTest {

    @Mock
    private IDirectoryDAO directoryDAO;

    @InjectMocks
    private PersonneConnexionValidator personneConnexionValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPersonneConnexionEmailVide() {
        Personne personne = new Personne();
        personne.setEmail("");
        personne.setMotDePasse("password");
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneConnexionValidator.validate(personne, errors);
        Assertions.assertTrue(errors.hasFieldErrors("email"));
        Assertions.assertEquals("email.vide", errors.getFieldError("email").getCode());
    }

    @Test
    void testPersonneConnexionMotDePasseVide() {
        Personne personne = new Personne();
        personne.setEmail("alice.dupont@test.com");
        personne.setMotDePasse("");
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneConnexionValidator.validate(personne, errors);
        Assertions.assertTrue(errors.hasFieldErrors("motDePasse"));
        Assertions.assertEquals("motDePasse.vide", errors.getFieldError("motDePasse").getCode());
    }

    @Test
    void testPersonneConnexionMotDePasseIncorrect() {
        Personne personne = new Personne();
        personne.setEmail("alice.dupont@test.com");
        personne.setMotDePasse("wrongpassword");
        Mockito.when(directoryDAO.authentifier(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneConnexionValidator.validate(personne, errors);
        Assertions.assertTrue(errors.hasFieldErrors("motDePasse"));
        Assertions.assertEquals("motDePasse.incorrect", errors.getFieldError("motDePasse").getCode());
    }
}