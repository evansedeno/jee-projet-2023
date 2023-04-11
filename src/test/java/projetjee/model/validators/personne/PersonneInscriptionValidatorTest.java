package projetjee.model.validators.personne;

import static org.junit.jupiter.api.Assertions.*;

import projetjee.Starter;
import projetjee.model.*;
import projetjee.model.objects.Groupe;
import projetjee.model.objects.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.validation.BeanPropertyBindingResult;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = Starter.class)
@AutoConfigureMockMvc

public class PersonneInscriptionValidatorTest {

    @Mock
    private IDirectoryDAO directoryDAO;

    @InjectMocks
    private InscriptionValidator personneValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSupports() {
        assertTrue(personneValidator.supports(Personne.class));
    }

    @Test
    public void testValidation() {
        Personne personne = new Personne();
        personne.setNom("honette");
        personne.setPrenom("camille");
        personne.setEmail("cam.hon@example.com");
        personne.setSiteWeb("http://camion.com");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("vroumvroum");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(anyInt())).thenReturn(null);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertEquals(1, errors.getErrorCount());
        assertEquals("groupe.existePas", errors.getFieldError("groupe").getCode());
    }

    @Test
    public void testValideDateNomTropCourt() {
        Personne personne = new Personne();
        personne.setNom("a");
        personne.setPrenom("Jean-philipe");
        personne.setEmail("jean.ph@gmail.com");
        personne.setSiteWeb("\"http://a.com\"");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("mdp");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(1)).thenReturn(groupe);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("nom.tropCourt", errors.getFieldError("nom").getCode());

    }

    @Test
    public void testValidationDateNomTropLong() {
        Personne personne = new Personne();
        personne.setNom("DupondDupondDupondDupondDupondDupondDupondDupondDupondDupondDupondDupondDupondDupondDupondDupond");
        personne.setPrenom("Léo-Paul");
        personne.setEmail("jpavcemalp.dupond@gmail.com");
        personne.setSiteWeb("\"http://jpavcemalp.com\"");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("mdp");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(1)).thenReturn(groupe);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("nom.tropLong", errors.getFieldError("nom").getCode());

    }

    @Test
    public void testValideDateNomVide() {
        Personne personne = new Personne();
        personne.setNom("");
        personne.setPrenom("Léo-Paul");
        personne.setEmail("jpavcemalp.dupond@gmail.com");
        personne.setSiteWeb("\"http://jpavcemalp.com\"");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("mdp");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(1)).thenReturn(groupe);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("nom.vide", errors.getFieldError("nom").getCode());

    }

    @Test
    public void testValideNomEspace() {
        Personne personne = new Personne();
        personne.setNom("Hyper vite");
        personne.setPrenom("Jean-Philippe");
        personne.setEmail("hypr.jp@gmail.com");
        personne.setSiteWeb("\"http://slip.com\"");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("vitevite");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(1)).thenReturn(groupe);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("nom.espace", errors.getFieldError("nom").getCode());
    }

    @Test
    public void testValideNomChiffre() {
        Personne personne = new Personne();
        personne.setNom("Puppetdu13");
        personne.setPrenom("Gaetan");
        personne.setEmail("p.g@gmail.com");
        personne.setSiteWeb("\"http://13.com\"");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("cmarseillebb");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(1)).thenReturn(groupe);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("nom.chiffre", errors.getFieldError("nom").getCode());
    }

    @Test
    public void testValideNomSpecial() {
        Personne personne = new Personne();
        personne.setNom("$*Puppet*$");
        personne.setPrenom("Gaetan");
        personne.setEmail("p.g@gmail.com");
        personne.setSiteWeb("\"http://13.com\"");
        personne.setDateDeNaissance(new Date(System.currentTimeMillis()));
        personne.setMotDePasse("cmarseillebb");
        Groupe groupe = new Groupe();
        groupe.setId(1);
        personne.setGroupe(groupe);
        when(directoryDAO.rechercherGroupeParId(1)).thenReturn(groupe);
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("nom.caractereSpecial", errors.getFieldError("nom").getCode());
    }

    @Test
    public void testValidationPrenomTropCourt() {
        Personne personne = new Personne();
        personne.setNom("Fenuzon");
        personne.setPrenom("A");
        personne.setSiteWeb("http://al.com");
        personne.setEmail("a.l@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        personne.setGroupe(new Groupe("Groupe A"));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("prenom"));
        assertEquals("prenom.tropCourt", errors.getFieldError("prenom").getCode());
    }

    @Test
    public void testValidationPrenomTropLong() {
        Personne personne = new Personne();
        personne.setNom("Fenuzon");
        personne.setPrenom("Jean-philipe-antoine-valentin-Charles-Edouard-Marc-Antoine-Léo-Paul");
        personne.setSiteWeb("http://al.com");
        personne.setEmail("a.l@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        personne.setGroupe(new Groupe("Groupe A"));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("prenom"));
        assertEquals("prenom.tropLong", errors.getFieldError("prenom").getCode());
    }

    @Test
    public void testValidationPrenomEspace() {
        Personne personne = new Personne();
        personne.setNom("Fenuzon");
        personne.setPrenom("Jean philipe");
        personne.setSiteWeb("http://jp.com");
        personne.setEmail("jp@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        personne.setGroupe(new Groupe("Groupe A"));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("prenom"));
        assertEquals("prenom.espace", errors.getFieldError("prenom").getCode());
    }

    @Test
    public void testValidationPrenomChiffre() {
        Personne personne = new Personne();
        personne.setNom("Fenuzon");
        personne.setPrenom("Jeanphilipedu13");
        personne.setSiteWeb("http://jp.com");
        personne.setEmail("jp@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        personne.setGroupe(new Groupe("Groupe A"));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("prenom"));
        assertEquals("prenom.chiffre", errors.getFieldError("prenom").getCode());
    }

    @Test
    public void testValidationPrenomSpecial() {
        Personne personne = new Personne();
        personne.setNom("Fenuzon");
        personne.setPrenom("*Jeanphilipe*");
        personne.setSiteWeb("http://jp.com");
        personne.setEmail("jp@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        personne.setGroupe(new Groupe("Groupe A"));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("prenom"));
        assertEquals("prenom.caractereSpecial", errors.getFieldError("prenom").getCode());
    }

    @Test
    public void testSiteWebVide() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("");
        personne.setEmail("Alice@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("siteWeb"));
        assertEquals("siteWeb.vide", errors.getFieldError("siteWeb").getCode());
    }

    @Test
    public void testSiteWebTropCourt() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("a");
        personne.setEmail("Alice@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("siteWeb"));
        assertEquals("siteWeb.tropCourt", errors.getFieldError("siteWeb").getCode());
    }

    @Test
    public void testSiteWebTropLong() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("https://www.example.com/" + "a".repeat(46));
        personne.setEmail("Alice@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("siteWeb"));
        assertEquals("siteWeb.tropLong", errors.getFieldError("siteWeb").getCode());
    }

    @Test
    public void testSiteWebAvecEspace() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("https://www.example. com");
        personne.setEmail("Alice@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("siteWeb"));
        assertEquals("siteWeb.espace", errors.getFieldError("siteWeb").getCode());
    }

    @Test
    public void testSiteWebFormatInvalide() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("Alice@gmail.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("siteWeb"));
        assertEquals("siteWeb.format", errors.getFieldError("siteWeb").getCode());
    }

    @Test
    public void testEmailVide() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("email"));
        assertEquals("email.vide", errors.getFieldError("email").getCode());
    }

    @Test
    public void testEmailTropCourt() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("a");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("email"));
        assertEquals("email.tropCourt", errors.getFieldError("email").getCode());
    }

    @Test
    public void testEmailTropLong() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("avefzukfgzuyfegeyfgzeufgefyuzagefiuezfgy@dfybgefgozeofygzeyofu.com");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("email"));
        assertEquals("email.tropLong", errors.getFieldError("email").getCode());
    }


    @Test
    public void testEmailFormatIncorrect() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("befyugyefgu");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("email"));
        assertEquals("email.format", errors.getFieldError("email").getCode());
    }

    @Test
    public void testGroupeVide() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("befyugyefgu");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("groupe"));
        assertEquals("groupe.vide", errors.getFieldError("groupe").getCode());
    }

    @Test
    public void testGroupeInexistant() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("befyugyefgu");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(new Date());
        Groupe groupe = new Groupe();
        groupe.setId(999);
        personne.setGroupe(groupe);
        Errors errors = new BindException(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("groupe"));
        assertEquals("groupe.existePas", errors.getFieldError("groupe").getCode());
    }

    @Test
    void testDateDeNaissanceInvalide() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("befyugyefgu");
        personne.setMotDePasse("password");
        personne.setDateDeNaissance(Date.from(LocalDate.of(2025, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("dateDeNaissance.invalide", errors.getFieldError("dateDeNaissance").getCode());
    }

    @Test
    void testMotDePasseVide() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("befyugyefgu");
        personne.setMotDePasse("");
        personne.setDateDeNaissance(Date.from(LocalDate.of(2025, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("motDePasse.vide", errors.getFieldError("motDePasse").getCode());
    }

    @Test
    void testMotDePasseTropCourt() {
        Personne personne = new Personne();
        personne.setNom("Dupont");
        personne.setPrenom("Alice");
        personne.setSiteWeb("feizofh");
        personne.setEmail("befyugyefgu");
        personne.setMotDePasse("a");
        personne.setDateDeNaissance(Date.from(LocalDate.of(2025, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        Errors errors = new BeanPropertyBindingResult(personne, "personne");
        personneValidator.validate(personne, errors);
        assertTrue(errors.hasErrors());
        assertEquals("motDePasse.tropCourt", errors.getFieldError("motDePasse").getCode());
    }

}



