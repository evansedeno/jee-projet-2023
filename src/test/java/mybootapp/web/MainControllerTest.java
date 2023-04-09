package mybootapp.controller;

import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MainControllerTest {

    @Mock
    private Utilisateur utilisateurMock;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MainController mainController = new MainController();
        mainController.setUtilisateur(utilisateurMock);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }
    
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testDeconnexion() throws Exception {
        Personne personne = new Personne();
        when(utilisateurMock.getPersonne()).thenReturn(personne);

        mockMvc.perform(get("/deconnexion"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(utilisateurMock, times(1)).setPersonne(null);
    }
}
