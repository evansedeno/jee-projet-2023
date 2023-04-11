package mybootapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mybootapp.model.objects.Utilisateur;
import mybootapp.model.validators.PersonneModificationMotDePasseValidator;
import mybootapp.model.validators.PersonneModificationValidator;
import mybootapp.model.validators.RechercheValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.Groupe;
import mybootapp.model.objects.Personne;

@ExtendWith(MockitoExtension.class)
public class PersonneControllerTest {

    @Mock
    private IDirectoryDAO directoryDAO;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private PersonneModificationValidator personneModificationValidator;

    @Mock
    private PersonneModificationMotDePasseValidator personneModificationMotDePasseValidator;

    @Mock
    private RechercheValidator rechercheValidator;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @InjectMocks
    private PersonneController personneController;

    private List<Personne> personnes;

    @BeforeEach
    public void setup() {
        personnes = new ArrayList<>();
        List<Groupe> groupes = new ArrayList<>();
        groupes.add(new Groupe("groupe1"));
        groupes.add(new Groupe("groupe2"));
        groupes.add(new Groupe( "groupe3"));
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
    public void testAfficherPersonne_WithValidId() {
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
        // Création d'un identifiant invalide
        long id = 0;
        when(directoryDAO.rechercherPersonneParId(id)).thenReturn(null);
        String viewName = personneController.afficherPersonne(id, model);
        assert (viewName.contains("redirect:/personne"));
        verify(directoryDAO).rechercherPersonneParId(id);
    }

    //TODO testModifierPersonne

    //TODO testModifierMotDePasse

    //TODO testSupprimerPersonne

    //TODO testRechercherPersonne

}