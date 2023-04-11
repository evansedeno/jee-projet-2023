package projetjee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import projetjee.model.objects.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Groupe;
import projetjee.model.objects.Utilisateur;
import projetjee.model.validators.GroupeValidator;

public class GroupeControllerTest {

    @Mock
    private IDirectoryDAO directoryDAO;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private GroupeController groupeController;

    @Mock
    private GroupeValidator groupeValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListerGroupes() {
        List<Groupe> groupes = new ArrayList<>();
        groupes.add(new Groupe());
        groupes.add(new Groupe());
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(groupes);
        String view = groupeController.listerGroupes(model);
        verify(model).addAttribute("groupes", groupes);
        verify(model).addAttribute("utilisateur", utilisateur);
        assert view.equals("groupes");
    }

    @Test
    public void testAfficherGroupe() {
        Groupe groupe = new Groupe();
        when(directoryDAO.rechercherGroupeParId(anyLong())).thenReturn(groupe);
        when(directoryDAO.rechercherPersonnesParGroupe(groupe)).thenReturn(new ArrayList<>());
        String view = groupeController.afficherGroupe(1L, model);
        verify(model).addAttribute("groupe", groupe);
        verify(model).addAttribute("personnes", new ArrayList<>());
        verify(model).addAttribute("utilisateur", utilisateur);
        assert view.equals("groupe");
    }


    @Test
    public void testAjouterGroupe() {
        Groupe groupe = new Groupe();
        groupe.setNom("Groupe de test");
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(true);
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = groupeController.ajouterGroupe(groupe, bindingResult, model);
        assertEquals("redirect:liste", viewName);
        verify(directoryDAO).ajouterGroupe(groupe);
    }

    @Test
    public void testAjouter100Groupes() {
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(false);
        for (int i = 0; i < 100; i++) {
            Groupe groupe = new Groupe();
            groupe.setNom("Groupe " + i);
            String viewName = groupeController.ajouterGroupe(groupe, bindingResult, model);
            assertEquals("redirect:liste", viewName);
            verify(directoryDAO).ajouterGroupe(groupe);
        }
    }


    @Test
    public void testAjouterGroupeAvecNomNull() {
        Groupe groupe = new Groupe();
        groupe.setNom(null);
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = groupeController.ajouterGroupe(groupe, bindingResult, model);
        assertEquals("ajouter-groupe", viewName);
        verify(directoryDAO, never()).ajouterGroupe(groupe);
    }

    @Test
    public void testAjouterGroupeAvecNomVide() {
        Groupe groupe = new Groupe();
        groupe.setNom("");
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = groupeController.ajouterGroupe(groupe, bindingResult, model);
        assertEquals("ajouter-groupe", viewName);
        verify(directoryDAO, never()).ajouterGroupe(groupe);
    }

    @Test
    public void testAjouterGroupeAvecNomTropLong() {
        Groupe groupe = new Groupe();
        groupe.setNom("frnzeoifjzreofizmehfnuzpeofhzenufpozhefzneuimoifhzeuifomizehfnuzileofmhzefj");
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = groupeController.ajouterGroupe(groupe, bindingResult, model);
        assertEquals("ajouter-groupe", viewName);
        verify(directoryDAO, never()).ajouterGroupe(groupe);

    }

    @Test
    public void testAjouterGroupeAvecNomTropCourt() {
        Groupe groupe = new Groupe();
        groupe.setNom("f");
        when(utilisateur.getPersonne()).thenReturn(new Personne());
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = groupeController.ajouterGroupe(groupe, bindingResult, model);
        assertEquals("ajouter-groupe", viewName);
        verify(directoryDAO, never()).ajouterGroupe(groupe);
    }
}