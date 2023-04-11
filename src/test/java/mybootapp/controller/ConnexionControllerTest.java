package mybootapp.controller;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.validators.personne.ConnexionValidator;
import mybootapp.Starter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Starter.class)
@AutoConfigureMockMvc
public class ConnexionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDirectoryDAO directoryDAO;

    @MockBean
    private ConnexionValidator connexionValidator;

    @MockBean
    private Utilisateur utilisateur;

    private Personne testPersonne;

    @BeforeEach
    public void setUp() {
        testPersonne = new Personne();
        testPersonne.setEmail("test@example.com");
        testPersonne.setMotDePasse("motdepasse");
    }

    @Test
    public void testConnexionFormulaire() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(null);
        mockMvc.perform(get("/connexion"))
                .andExpect(status().isOk())
                .andExpect(view().name("connexion-personne"));
    }

    @Test
    public void testConnexionFormulaireDejaConnecte() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(testPersonne);
        mockMvc.perform(get("/connexion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testConnexionValide() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(null);
        when(connexionValidator.supports(any())).thenReturn(true);
        doNothing().when(connexionValidator).validate(any(), any(BindingResult.class));
        when(directoryDAO.authentifier(testPersonne.getEmail(), testPersonne.getMotDePasse())).thenReturn(testPersonne);

        mockMvc.perform(post("/connexion")
                        .param("email", testPersonne.getEmail())
                        .param("motDePasse", testPersonne.getMotDePasse()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(utilisateur, times(1)).setPersonne(testPersonne);
    }

    @Test
    public void testConnexionInvalide() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(null);
        when(connexionValidator.supports(any())).thenReturn(true);

        ConnexionController controller = new ConnexionController();
        controller.directoryDAO = directoryDAO;
        controller.personneConnexionValidator = connexionValidator;
        controller.utilisateur = utilisateur;

        MockMvc mockMvc = standaloneSetup(controller).build();

        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            BindingResult bindingResult = (BindingResult) args[1];
            bindingResult.reject("motDePasse.incorrect");
            return null;
        }).when(connexionValidator).validate(any(), any(BindingResult.class));

        mockMvc.perform(post("/connexion")
                        .param("email", testPersonne.getEmail())
                        .param("motDePasse", testPersonne.getMotDePasse()))
                .andExpect(status().isOk())
                .andExpect(view().name("connexion-personne"))
                .andExpect(model().attributeHasErrors("personne"));
    }
}