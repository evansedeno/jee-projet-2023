package mybootapp.model;

import mybootapp.controller.DirectoryDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DirectoryDAOImpl implements DirectoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Groupe> trouverTousLesGroupes() {
        return entityManager.createQuery("SELECT g FROM Groupe g", Groupe.class).getResultList();
    }

    public Groupe trouverGroupeParId(long id) {
        return entityManager.find(Groupe.class, id);
    }

    public List<Personne> trouverPersonnesParGroupe(Groupe groupe) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE p.groupe = :groupe", Personne.class)
                .setParameter("groupe", groupe).getResultList();
    }

    public Personne trouverPersonneParId(long id) {
        return entityManager.find(Personne.class, id);
    }

    public List<Personne> rechercherPersonnesParNom(String nom) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE LOWER(p.nom) LIKE LOWER(:nom)", Personne.class)
                .setParameter("nom", "%" + nom.toLowerCase() + "%").getResultList();
    }

    public List<Personne> rechercherPersonnesParPrenom(String prenom) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE LOWER(p.prenom) LIKE LOWER(:prenom)", Personne.class)
                .setParameter("prenom", "%" + prenom.toLowerCase() + "%").getResultList();
    }

    public List<Personne> rechercherPersonnesParSiteWeb(String siteWeb) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE LOWER(p.siteWeb) LIKE LOWER(:siteWeb)", Personne.class)
                .setParameter("siteWeb", siteWeb).getResultList();
    }

    public void ajouterPersonne(Personne personne) {
        entityManager.persist(personne);
    }

    public void ajouterGroupe(Groupe groupe) {
        entityManager.persist(groupe);
    }

    public List<Personne> trouverToutesLesPersonnes() {
        return entityManager.createQuery("SELECT p FROM Personne p", Personne.class).getResultList();
    }
}