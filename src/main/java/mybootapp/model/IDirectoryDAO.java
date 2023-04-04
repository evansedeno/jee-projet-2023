package mybootapp.model;

import java.util.List;

public interface IDirectoryDAO {

    /* ----------------- Groupe ----------------- */

    // Recherches
    public List<Groupe> rechercherTousLesGroupes();
    public Groupe rechercherGroupeParId(long id);
    public List<Groupe> rechercherGroupeParNom(String nom);

    // Ajouts
    public void ajouterGroupe(Groupe groupe);

    // Suppressions
    public void supprimerGroupe(Groupe groupe);

    // Modifications
    public void modifierGroupe(Groupe groupe);


    /* ----------------- Personne ----------------- */

    // Recherches
    public List<Personne> rechercherToutesLesPersonnes();
    public List<Personne> rechercherPersonnesParGroupe(Groupe groupe);
    public Personne rechercherPersonneParId(long id);
    public List<Personne> rechercherPersonnesParNom(String nom);
    public Personne rechercherPersonneParEmail(String email);
    public List<Personne> rechercherPersonnesParPrenom(String prenom);
    public List<Personne> rechercherPersonnesParSiteWeb(String siteWeb);
    public boolean authentifier(String email, String password);

    // Ajouts
    public void ajouterPersonne(Personne personne);

    // Suppressions
    public void supprimerPersonne(Personne personne);

    // Modifications
    public void modifierPersonne(Personne personne);
}