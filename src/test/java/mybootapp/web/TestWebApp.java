package mybootapp.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import mybootapp.controller.DirectoryController;
import mybootapp.controller.DirectoryService;
import mybootapp.model.Groupe;
import mybootapp.model.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class DirectoryControllerTest {

    @Mock
    private DirectoryService directoryService;

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private DirectoryController directoryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(directoryController).build();
    }


    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void TestcreatePersonne() {
        Groupe groupe = new Groupe();
        Date dateNaissance = new Date(0);
        Personne p = new Personne("John", "Cena", "John.Cena@wwf.com", "johnflex.io", dateNaissance, groupe, "toto");
        assertEquals("John", p.getNom());
        assertEquals("Cena", p.getPrenom());
        assertEquals("John.Cena@wwf.com", p.getEmail());
        assertEquals("johnflex.io", p.getSiteWeb());
        assertEquals(dateNaissance, p.getDateDeNaissance());
        assertEquals(groupe, p.getGroupe());
        assertEquals("toto", p.getMotDePasse());
    }


    @Test
    public void testTrouverGroupeParId() {
        DirectoryService directoryService = Mockito.mock(DirectoryService.class);

        Groupe groupe = new Groupe("Groupe 1");
        directoryService.ajouterGroupe(groupe);
        when(directoryService.trouverGroupeParId(1)).thenReturn(groupe);

        Groupe result = directoryService.trouverGroupeParId(1);
        Assertions.assertEquals("Groupe 1", result.getNom());
    }

    @Test
    public void testTrouverPersonnesParGroupe() {
        Groupe groupe = new Groupe();
        groupe.setId(1);
        when(directoryService.trouverPersonnesParGroupe(groupe)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryService.trouverPersonnesParGroupe(groupe);

        Assertions.assertEquals(2, result.size());
    }


    @Test
    public void testTrouverPersonneParId() {
        Personne personne = new Personne();
        personne.setId(1);
        when(directoryService.trouverPersonneParId(1)).thenReturn(personne);

        Personne result = directoryService.trouverPersonneParId(1);

        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void testRechercherPersonnesParNom() {
        String nom = "Macron";
        when(directoryService.rechercherPersonnesParNom(nom)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryService.rechercherPersonnesParNom(nom);

        Assertions.assertEquals(2, result.size());
    }


    @Test
    public void testRechercherPersonnesParPrenom() {
        String prenom = "Manu";
        when(directoryService.rechercherPersonnesParPrenom(prenom)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryService.rechercherPersonnesParPrenom(prenom);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testRechercherPersonnesParSiteWeb() {
        String website = "www.49,3.com";
        when(directoryService.rechercherPersonnesParSiteWeb(website)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryService.rechercherPersonnesParSiteWeb(website);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testAjouterPersonne() {
        Personne personne = new Personne();
        directoryService.ajouterPersonne(personne);
        verify(directoryService, times(1)).ajouterPersonne(personne);
    }

    @Test
    public void testAjouterGroupe() {
        Groupe groupe = new Groupe();
        directoryService.ajouterGroupe(groupe);
        verify(directoryService, times(1)).ajouterGroupe(groupe);
    }


}