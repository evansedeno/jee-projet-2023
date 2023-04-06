package mybootapp.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Recherche {

    @Autowired
    private IDirectoryDAO directoryDAO;

    public List<Personne> rechercherPersonne(String id, String nom, String prenom, String siteWeb) {
        List<Personne> personnes = new ArrayList<>();
        if (id == null && nom == null && prenom == null && siteWeb == null) {
            return personnes;
        }
        if (id != null && !id.isEmpty() && Long.parseLong(id) > 0) {
            Personne personne = directoryDAO.rechercherPersonneParId(Long.parseLong(id));
            personnes.add(personne);
            return personnes;
        }
        if (nom != null && !nom.isEmpty() && prenom != null && !prenom.isEmpty()) {
            personnes = directoryDAO.rechercherPersonnesParNomEtPrenom(nom, prenom);
            return personnes;
        }
        if (nom != null && !nom.isEmpty() && prenom == null && prenom.isEmpty()) {
            personnes = directoryDAO.rechercherPersonnesParNom(nom);
            return personnes;
        }
        if (prenom != null && !prenom.isEmpty() && nom == null && nom.isEmpty()) {
            personnes = directoryDAO.rechercherPersonnesParPrenom(prenom);
            return personnes;
        }
        if (siteWeb != null && !siteWeb.isEmpty() && nom == null && nom.isEmpty() && prenom == null && prenom.isEmpty()) {
            personnes = directoryDAO.rechercherPersonnesParSiteWeb(siteWeb);
            return personnes;
        }
        return personnes;
    }

    public List<Groupe> rechercherGroupes(String id, String nom) {
        List<Groupe> groupes = new ArrayList<>();
        if (id == null && nom == null) {
            return groupes;
        }
        if (id != null && !id.isEmpty() && Long.parseLong(id) > 0) {
            Groupe groupe = directoryDAO.rechercherGroupeParId(Long.parseLong(id));
            groupes.add(groupe);
            return groupes;
        }
        if (nom != null && !nom.isEmpty()) {
            groupes = directoryDAO.rechercherGroupeParNom(nom);
            return groupes;
        }
        return groupes;
    }
}
