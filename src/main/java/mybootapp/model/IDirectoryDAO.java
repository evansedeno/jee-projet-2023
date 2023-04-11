package mybootapp.model;

import mybootapp.model.objects.Groupe;
import mybootapp.model.objects.JetonMotDePasse;
import mybootapp.model.objects.Personne;

import java.util.List;

public interface IDirectoryDAO {

    /* ----------------- Groupe ----------------- */

    // Recherches
    List<Groupe> rechercherTousLesGroupes();

    Groupe rechercherGroupeParId(long id);

    // Ajouts
    void ajouterGroupe(Groupe groupe);


    /* ----------------- Personne ----------------- */

    // Recherches
    List<Personne> rechercherToutesLesPersonnes();

    List<Personne> rechercherPersonnesParGroupe(Groupe groupe);

    Personne rechercherPersonneParId(long id);

    List<Personne> rechercherPersonnesParNom(String nom);

    Personne rechercherPersonneParEmail(String email);

    List<Personne> rechercherPersonnesParPrenom(String prenom);

    List<Personne> rechercherPersonnesParSiteWeb(String siteWeb);

    Personne authentifier(String email, String password);

    // Ajouts
    void ajouterPersonne(Personne personne);

    // Suppressions
    void supprimerPersonne(Personne personne);

    // Modifications
    void modifierPersonne(Personne personne);


    /* ----------------- JetonMotDePasse ----------------- */
    List<JetonMotDePasse> rechercherTousLesJetons();

    void supprimerJeton(JetonMotDePasse jeton);

    void ajouterJeton(JetonMotDePasse jetonMotDePasse);

    JetonMotDePasse rechercherJetonParToken(String token);
}