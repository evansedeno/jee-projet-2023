package mybootapp.controller;

import mybootapp.controller.MotDePasseController;
import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.JetonMotDePasse;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.services.JetonMotDePasseService;
import mybootapp.model.validators.motdepasse.MotDePasseOublieValidator;
import mybootapp.model.validators.motdepasse.JetonValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MotDePasseControllerTest {

    @InjectMocks
    private MotDePasseController motDePasseController;

    @Mock
    private IDirectoryDAO directoryDAO;

    @Mock
    private MotDePasseOublieValidator motDePasseOublieValidator;

    @Mock
    private JetonValidator jetonValidator;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private JetonMotDePasseService jetonMotDePasseService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        when(utilisateur.getPersonne()).thenReturn(null);
    }

    @Test
    public void testMotDePasseOublieFormulaire() {
        String viewName = motDePasseController.motDePassOublieFormulaire(model);
        verify(model).addAttribute("personne", new Personne());
        assertEquals("reinitialiser-motdepasse", viewName);
    }

    @Test
    public void testMotDePasseOublie() {
        Personne personne = new Personne();
        personne.setEmail("test@example.com");
        when(directoryDAO.rechercherPersonneParEmail(personne.getEmail())).thenReturn(personne);
        when(jetonMotDePasseService.creerJeton(personne)).thenReturn(new JetonMotDePasse());

        String viewName = motDePasseController.motDePassOublie(personne, bindingResult, model);
        verify(motDePasseOublieValidator).validate(personne, bindingResult);
        verify(jetonMotDePasseService).envoyerEmail(any(JetonMotDePasse.class));
        verify(model).addAttribute("emailEnvoie", true);
        assertEquals("reinitialiser-motdepasse", viewName);
    }

    @Test
    public void testChangementMotDePasseFormulaire() {
        String token = "validToken";
        JetonMotDePasse jetonMotDePasse = new JetonMotDePasse();
        when(directoryDAO.rechercherJetonParToken(token)).thenReturn(jetonMotDePasse);

        String viewName = motDePasseController.changementMotDePasseFormulaire(token, model);
        verify(model).addAttribute("jetonMotDePasse", jetonMotDePasse);
        assertEquals("jeton-motdepasse", viewName);
    }

    @Test
    public void testChangementMotDePasse() {
        String token = "validToken";
        JetonMotDePasse jetonMotDePasse = new JetonMotDePasse();
        jetonMotDePasse.setToken(token);
        jetonMotDePasse.setNouveauMotDePasse("newPassword");
        jetonMotDePasse.setConfirmationMotDePasse("newPassword");
        Personne personne = new Personne();
        jetonMotDePasse.setPersonne(personne);

        when(directoryDAO.rechercherJetonParToken(token)).thenReturn(jetonMotDePasse);

        String viewName = motDePasseController.changementMotDePasse(token, jetonMotDePasse, bindingResult, model);

        verify(jetonValidator).validate(jetonMotDePasse, bindingResult);
        verify(directoryDAO).modifierPersonne(personne);
        verify(model).addAttribute("motDePasseModifie", true);
        assertEquals("jeton-motdepasse", viewName);
    }
}