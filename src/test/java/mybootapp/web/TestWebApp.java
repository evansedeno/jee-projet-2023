package mybootapp.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import mybootapp.model.IDirectoryDAO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


import mybootapp.controller.MainController;
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
    private IDirectoryDAO directoryDAO;

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private MainController directoryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(directoryController).build();
    }


    @Test
    public void testIndex() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }



    @Test
    public void TestControllerInscriptionFormulaire() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/inscription"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("connexion"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groupes"));
    }
    
    @Test
    public  void TestInscription() throws Exception{
        Groupe groupe = new Groupe();
        groupe.setNom("La bande à Picsou");
        groupe.setId(1);
        Personne personne = new Personne();
        personne.setNom("Picsou");
        personne.setPrenom("Balthazar");
        personne.setEmail("Balthazar.Picsou@donaldville.com");
        personne.setDateDeNaissance(new Date());
        personne.setSiteWeb("baltpcs.com");
        personne.setMotDePasse("Sou Fetiche");
        personne.setGroupe(groupe);
        Mockito.verify(directoryDAO, Mockito.times(1)).ajouterPersonne(personne);

        mockMvc.perform(MockMvcRequestBuilders.post("/inscription")
                        .param("nom", "Picsou")
                        .param("prenom", "Balthazar")
                        .param("email", "Balthazar.Picsou@donaldville.com")
                        .param("dateDeNaissance", "01-01-1900")
                        .param("siteWeb", "baltpcs.com")
                        .param("motDePasse", "Sou Fetiche")
                        .param("groupe.nom", "1"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.view().name("inscription"))
                        .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
        Mockito.verify(directoryDAO, Mockito.never()).ajouterPersonne(Mockito.any(Personne.class));

    }


    @Test
    public void TestcreatePersonne(){
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
    public void testTrouverGroupeParId(){
        IDirectoryDAO directoryService = Mockito.mock(IDirectoryDAO.class);

        Groupe groupe = new Groupe("Groupe 1");
        directoryService.ajouterGroupe(groupe);
        when(directoryService.rechercherGroupeParId(1)).thenReturn(groupe);

        Groupe result = directoryService.rechercherGroupeParId(1);
        Assertions.assertEquals("Groupe 1", result.getNom());
    }

    @Test
    public void testTrouverPersonnesParGroupe(){
        Groupe groupe = new Groupe();
        groupe.setId(1);
        when(directoryDAO.rechercherPersonnesParGroupe(groupe)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryDAO.rechercherPersonnesParGroupe(groupe);

        Assertions.assertEquals(2, result.size());
    }


    @Test
    public void testTrouverPersonneParId(){
        Personne personne = new Personne();
        personne.setId(1);
        when(directoryDAO.rechercherPersonneParId(1)).thenReturn(personne);

        Personne result = directoryDAO.rechercherPersonneParId(1);

        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void testRechercherPersonnesParNom(){
        String nom = "Macron";
        when(directoryDAO.rechercherPersonnesParNom(nom)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryDAO.rechercherPersonnesParNom(nom);

        Assertions.assertEquals(2, result.size());
    }


    @Test
    public void testRechercherPersonnesParPrenom(){
        String prenom = "Manu";
        when(directoryDAO.rechercherPersonnesParPrenom(prenom)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryDAO.rechercherPersonnesParPrenom(prenom);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testRechercherPersonnesParSiteWeb(){
        String website = "www.49,3.com";
        when(directoryDAO.rechercherPersonnesParSiteWeb(website)).thenReturn(Arrays.asList(new Personne(), new Personne()));

        List<Personne> result = directoryDAO.rechercherPersonnesParSiteWeb(website);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testAjouterPersonne(){
        Personne personne = new Personne();
        directoryDAO.ajouterPersonne(personne);
        verify(directoryDAO, times(1)).ajouterPersonne(personne);
    }

    @Test
    public void testAjouterGroupe(){
        Groupe groupe = new Groupe();
        directoryDAO.ajouterGroupe(groupe);
        verify(directoryDAO, times(1)).ajouterGroupe(groupe);
    }


    @Test
    public void testCreateGroupeModelAttribute() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("command"))
                .andExpect(MockMvcResultMatchers.model().attribute("command", Matchers.instanceOf(Groupe.class)));
    }

    @Test
    public void testAfficherFormulaireAjoutGroupe() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/ajouter-groupe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ajouter-groupes"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("groupe"));
    }



}