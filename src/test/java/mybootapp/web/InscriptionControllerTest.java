package mybootapp.controller;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Groupe;
import mybootapp.model.objects.Personne;
import mybootapp.model.objects.Utilisateur;
import mybootapp.model.validators.PersonneConnexionValidator;
import mybootapp.model.validators.PersonneInscriptionValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InscriptionControllerTest {

    @Mock
    private IDirectoryDAO directoryDAOMock;

    @Mock
    private PersonneInscriptionValidator personneInscriptionValidatorMock;

    @Mock
    private Utilisateur utilisateurMock;

    private MockMvc mockMvc;

    @InjectMocks
    private InscriptionController inscriptionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(inscriptionController).build();
    }

    @Test
    public void testInscriptionFormulaire() throws Exception {
        List<Groupe> groupes = new ArrayList<>();
        groupes.add(new Groupe("Groupe 1"));
        groupes.add(new Groupe( "Groupe 2"));
        when(utilisateurMock.getPersonne()).thenReturn(null);
        when(directoryDAOMock.rechercherTousLesGroupes()).thenReturn(groupes);
        mockMvc.perform(MockMvcRequestBuilders.get("/inscriptions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("inscription"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groupes"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("personne"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("utilisateur"))
                .andExpect(MockMvcResultMatchers.model().attribute("groupes", groupes))
                .andExpect(MockMvcResultMatchers.model().attribute("utilisateur", utilisateurMock));
    }

    @Test
    public void testInscriptionFormulaireUtilisateurConnecte() throws Exception {
        Personne p = new Personne("John", "Cena", "John.Cena@wwf.com", "johnflex.io", new Date(), new Groupe(), "toto");
        when(utilisateurMock.getPersonne()).thenReturn(p);
        mockMvc.perform(MockMvcRequestBuilders.get("/inscriptions"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    @Test
    public void testInscriptionValider() throws Exception {
        Personne p = new Personne("John", "Cena", "John.Cena@wwf.com", "johnflex.io", new Date(), new Groupe(), "toto");
        when(utilisateurMock.getPersonne()).thenReturn(null);
        when(personneInscriptionValidatorMock.supports(any())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/inscriptions")
                        .param("nom", "Cena")
                        .param("prenom", "John")
                        .param("email", "")
                        .param("telephone", "johnflex.io")
                        .param("dateNaissance", "2020-01-01")
                        .param("groupe", "1")
                        .param("motDePasse", "toto")
                        .param("motDePasseConfirmation", "toto"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
        verify(directoryDAOMock, times(1)).ajouterPersonne(any());

    }

    @Test
    public void testInscriptionValiderUtilisateurConnecte() throws Exception {
        Personne p = new Personne("John", "Cena", "John.Cena@wwf.com", "johnflex.io", new Date(), new Groupe(), "toto");
        when(utilisateurMock.getPersonne()).thenReturn(p);
        mockMvc.perform(MockMvcRequestBuilders.post("/inscriptions"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }
}
