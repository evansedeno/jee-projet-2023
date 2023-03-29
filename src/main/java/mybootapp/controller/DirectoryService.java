package mybootapp.controller;

import mybootapp.model.Groupe;
import mybootapp.model.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DirectoryService {

    @Autowired
    private DirectoryDAO directoryDAO;

    public List<Groupe> trouverTousLesGroupes() {
        return directoryDAO.trouverTousLesGroupes();
    }

    public Groupe trouverGroupeParId(long id) {
        return directoryDAO.trouverGroupeParId(id);
    }

    public List<Personne> trouverPersonnesParGroupe(Groupe groupe) {
        return directoryDAO.trouverPersonnesParGroupe(groupe);
    }

    public Personne trouverPersonneParId(long id) {
        return directoryDAO.trouverPersonneParId(id);
    }

    public List<Personne> rechercherPersonnesParNom(String nom) {
        return directoryDAO.rechercherPersonnesParNom(nom);
    }

    public List<Personne> rechercherPersonnesParPrenom(String prenom) {
        return directoryDAO.rechercherPersonnesParPrenom(prenom);
    }

    public List<Personne> rechercherPersonnesParSiteWeb(String siteWeb) {
        return directoryDAO.rechercherPersonnesParSiteWeb(siteWeb);
    }

    public void ajouterPersonne(Personne personne) {
        directoryDAO.ajouterPersonne(personne);
    }

    public void ajouterGroupe(Groupe groupe) {
        directoryDAO.ajouterGroupe(groupe);
    }

    public List<Personne> trouverToutesLesPersonnes() {
        return directoryDAO.trouverToutesLesPersonnes();}
}