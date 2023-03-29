package mybootapp.controller;

import mybootapp.model.Groupe;
import mybootapp.model.Personne;

import java.util.List;

public interface DirectoryDAO {

    public List<Groupe> trouverTousLesGroupes();

    public Groupe trouverGroupeParId(long id);

    public List<Personne> trouverPersonnesParGroupe(Groupe groupe);

    public Personne trouverPersonneParId(long id);

    public List<Personne> rechercherPersonnesParNom(String nom);

    public List<Personne> rechercherPersonnesParPrenom(String prenom);

    public void ajouterPersonne(Personne personne);

    public void ajouterGroupe(Groupe groupe);

    public List<Personne> trouverToutesLesPersonnes();

    List<Personne> rechercherPersonnesParSiteWeb(String siteWeb);
}