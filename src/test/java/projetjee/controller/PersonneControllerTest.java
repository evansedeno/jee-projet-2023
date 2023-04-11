package projetjee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import projetjee.model.objects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import projetjee.model.IDirectoryDAO;
import projetjee.model.validators.RechercheValidator;
import projetjee.model.validators.motdepasse.MotDePasseChangementValidator;

@ExtendWith(MockitoExtension.class)
public class PersonneControllerTest {

    @Mock
    private IDirectoryDAO directoryDAO;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @InjectMocks
    private PersonneController personneController;

    @Mock
    private RechercheValidator rechercheValidator;

    @Mock
    private MotDePasseChangementValidator motDePasseChangementValidator;


    private List<Personne> personnes;

    @BeforeEach
    public void setup() {
        personnes = new ArrayList<>();
        List<Groupe> groupes = new ArrayList<>();
        groupes.add(new Groupe("groupe1"));
        groupes.add(new Groupe("groupe2"));
        groupes.add(new Groupe("groupe3"));
        for (int i = 1; i <= 10; i++) {
            Personne personne = new Personne();
            personne.setId(i);
            personne.setNom("Nom " + i);
            personne.setPrenom("Prenom " + i);
            personne.setEmail("email" + i + "@gmail.com");
            personne.setSiteWeb("www.site" + i + ".com");
            personne.setDateDeNaissance(new Date());
            personne.setGroupe(groupes.get((int) (Math.random() * 3)));
            personne.setMotDePasse("motdepasse" + i);
            personnes.add(personne);
        }
    }

    @Test
    public void testListerPersonnes() {
        when(directoryDAO.rechercherToutesLesPersonnes()).thenReturn(personnes);
        String viewName = personneController.listerPersonnes(model);
        verify(model).addAttribute("personnes", personnes);
        verify(model).addAttribute("groupes", directoryDAO.rechercherTousLesGroupes());
        verify(model).addAttribute("utilisateur", utilisateur);
        assert (viewName.equals("personnes"));
    }

    @Test
    public void testAfficherPersonneAvecIdValide() {
        long id = 1;
        Personne personne = personnes.get(0);
        when(directoryDAO.rechercherPersonneParId(id)).thenReturn(personne);
        String viewName = personneController.afficherPersonne(id, model);
        verify(model).addAttribute("personne", personne);
        verify(model).addAttribute("utilisateur", utilisateur);
        assert (viewName.equals("personne"));
    }

    @Test
    public void testAfficherPersonne_WithInvalidId() {
        long id = 0;
        when(directoryDAO.rechercherPersonneParId(id)).thenReturn(null);
        String viewName = personneController.afficherPersonne(id, model);
        assert (viewName.contains("redirect:/personne"));
        verify(directoryDAO).rechercherPersonneParId(id);
    }

    @Test
    public void testModifierPersonne() {
        long id = 1;
        Personne personne = personnes.get(0);
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        when(utilisateur.getPersonne()).thenReturn(personne);
        when(directoryDAO.rechercherPersonneParId(id)).thenReturn(personne);
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(groupes);

        String viewName = personneController.modifierPersonne(id, model);

        verify(model).addAttribute("personne", personne);
        verify(model).addAttribute("groupes", groupes);
        verify(model).addAttribute("utilisateur", utilisateur);
        assertEquals("modifier-personne", viewName);
    }

    @Test
    public void testMotDePasseChangement() {
        long id = 1;
        Personne personne = personnes.get(0);
        ChangementMotDePasse changementMotDePasse = new ChangementMotDePasse();
        when(utilisateur.getPersonne()).thenReturn(personne);

        String viewName = personneController.motDePasseChangement(id, changementMotDePasse, model, bindingResult);

        verify(model).addAttribute("utilisateur", utilisateur);
        assertEquals("modifier-personne", viewName);
    }

    @Test
    public void testSupprimerPersonne() {
        long id = 1;
        Personne personne = personnes.get(0);
        when(utilisateur.getPersonne()).thenReturn(personne);
        when(directoryDAO.rechercherPersonneParId(id)).thenReturn(personne);

        String viewName = personneController.supprimerPersonne(id);

        verify(directoryDAO).supprimerPersonne(personne);
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testRechercherPersonnesGet() {
        List<Groupe> groupes = new ArrayList<>();
        groupes.add(new Groupe("groupe 1"));
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(groupes);

        String viewName = personneController.rechercherPersonnes(model);

        verify(model).addAttribute("groupes", groupes);
        verify(model).addAttribute("utilisateur", utilisateur);
        assertEquals("rechercher", viewName);
    }

    @Test
    public void testRechercherPersonnesPost() {
        Groupe groupe = new Groupe("groupe 1");
        directoryDAO.ajouterGroupe(groupe);
        Recherche recherche = new Recherche("1", "Nom 1", "Prenom 1", "www.site1.com", "0", directoryDAO);
        List<Groupe> groupes = directoryDAO.rechercherTousLesGroupes();
        when(directoryDAO.rechercherTousLesGroupes()).thenReturn(groupes);

        String viewName = personneController.rechercherPersonnes(recherche, bindingResult, model);

        verify(model).addAttribute("groupes", groupes);
        verify(model).addAttribute("personnes", recherche.rechercherPersonnes());
        assertEquals("rechercher", viewName);
    }

}