package mybootapp.controller;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.validators.PersonneConnexionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConnexionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IDirectoryDAO directoryDAOMock;

    @Mock
    private PersonneConnexionValidator personneConnexionValidatorMock;

    @Mock
    private Utilisateur utilisateurMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MainController mainController = new MainController();
        mainController.setUtilisateur(utilisateurMock);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }


    @Test
    public void testConnexionForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/connexion"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("connexion"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("personne"));
    }

    @Test
    public void testConnexionPost() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("connexion"));
    }

    @Test
    public void testConnexionPostWithErrors() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .param("email", "szszsz")
                        .param("password", "szszsz"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("connexion"));
    }

    @Test
    public void testConnexionPostWithSuccess() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);
        when(directoryDAOMock.rechercherPersonneParEmail(any())).thenReturn(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .param("email", "szszsz")
                        .param("password", "szszsz"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void testConnexionPostWithSuccessAndRedirect() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);
        when(directoryDAOMock.rechercherPersonneParEmail(any())).thenReturn(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .param("email", "szszsz")
                        .param("password", "szszsz")
                        .param("redirect", "/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void testConnexionPostWithSuccessAndRedirectWithParams() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);
        when(directoryDAOMock.rechercherPersonneParEmail(any())).thenReturn(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .param("email", "szszsz")
                        .param("password", "szszsz")
                        .param("redirect", "/?param1=1&param2=2"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/?param1=1&param2=2"));
    }

    @Test
    public void testConnexionPostWithSuccessAndRedirectWithParamsAndAnchor() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);
        when(directoryDAOMock.rechercherPersonneParEmail(any())).thenReturn(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/connexion")
                        .param("email", "szszsz")
                        .param("password", "szszsz")
                        .param("redirect", "/?param1=1&param2=2#anchor"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/?param1=1&param2=2#anchor"));
    }


}
