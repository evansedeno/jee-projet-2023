package projetjee.controller;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Groupe;
import projetjee.model.objects.Personne;
import projetjee.model.objects.Utilisateur;
import projetjee.model.validators.personne.InscriptionValidator;
import projetjee.Starter;
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

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Starter.class)
@AutoConfigureMockMvc
public class InscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDirectoryDAO directoryDAO;

    @MockBean
    private InscriptionValidator inscriptionValidator;

    @MockBean
    private Utilisateur utilisateur;

    private Personne testPersonne;
    private Groupe testGroupe;

    @BeforeEach
    public void setUp() {
        testPersonne = new Personne();
        testPersonne.setId(1);
        testPersonne.setPrenom("John");
        testPersonne.setNom("Doe");
        testPersonne.setEmail("john.doe@example.com");
        testPersonne.setMotDePasse("motdepasse");

        testGroupe = new Groupe();
        testGroupe.setId(1);
        testGroupe.setNom("Groupe de test");
    }

    @Test
    public void testInscriptionFormulaire() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(null);
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(Arrays.asList(testGroupe));

        mockMvc.perform(get("/inscription"))
                .andExpect(status().isOk())
                .andExpect(view().name("inscription-personne"))
                .andExpect(model().attributeExists("groupes", "personne", "utilisateur"));
    }

    @Test
    public void testInscriptionFormulaireDejaConnecte() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(testPersonne);

        mockMvc.perform(get("/inscription"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testInscriptionValide() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(null);
        when(inscriptionValidator.supports(any())).thenReturn(true);
        doNothing().when(inscriptionValidator).validate(any(), any());
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(Arrays.asList(testGroupe));
        when(directoryDAO.rechercherGroupeParId(testGroupe.getId())).thenReturn(testGroupe);

        mockMvc.perform(post("/inscription")
                        .param("prenom", testPersonne.getPrenom())
                        .param("nom", testPersonne.getNom())
                        .param("email", testPersonne.getEmail())
                        .param("motDePasse", testPersonne.getMotDePasse())
                        .param("groupe.nom", String.valueOf(testGroupe.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testInscriptionInvalide() throws Exception {
        when(utilisateur.getPersonne()).thenReturn(null);
        when(inscriptionValidator.supports(any())).thenReturn(true);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            BindingResult bindingResult = (BindingResult) args[1];
            bindingResult.rejectValue("email", "email.incorrect");
            return null;
        }).when(inscriptionValidator).validate(any(), any());
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(Arrays.asList(testGroupe));

        mockMvc.perform(post("/inscription")
                        .param("prenom", testPersonne.getPrenom())
                        .param("nom", testPersonne.getNom())
                        .param("email", "invalid_email")
                        .param("motDePasse", testPersonne.getMotDePasse())
                        .param("groupe.nom", String.valueOf(testGroupe.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("inscription-personne"))
                .andExpect(model().attributeHasErrors("personne"))
                .andExpect(model().attributeHasFieldErrors("personne", "email"));
    }
}
