package mybootapp.model;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EntityManagerDirectoryDAO implements IDirectoryDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /* ----------------- Groupe ----------------- */

    // Recherches
    public List<Groupe> rechercherTousLesGroupes() {
        return entityManager.createQuery("SELECT g FROM Groupe g", Groupe.class).getResultList();
    }

    public Groupe rechercherGroupeParId(long id) {
        return entityManager.find(Groupe.class, id);
    }

    public List<Groupe> rechercherGroupeParNom(String nom) {
        return entityManager.createQuery("SELECT g FROM Groupe g WHERE LOWER(g.nom) LIKE LOWER(:nom)", Groupe.class)
                .setParameter("nom", "%" + nom.toLowerCase() + "%").getResultList();
    }

    // Ajouts
    public void ajouterGroupe(Groupe groupe) {
        entityManager.persist(groupe);
    }

    // Suppressions
    public void supprimerGroupe(Groupe groupe) {
        entityManager.remove(groupe);
    }

    // Modifications
    public void modifierGroupe(Groupe groupe) {
        entityManager.merge(groupe);
    }


    /* ----------------- Personne ----------------- */

    // Recherches
    public List<Personne> rechercherToutesLesPersonnes() {
        return entityManager.createQuery("SELECT p FROM Personne p", Personne.class).getResultList();
    }

    public List<Personne> rechercherPersonnesParGroupe(Groupe groupe) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE p.groupe = :groupe", Personne.class)
                .setParameter("groupe", groupe).getResultList();
    }

    public Personne rechercherPersonneParId(long id) {
        return entityManager.find(Personne.class, id);
    }

    public List<Personne> rechercherPersonnesParNom(String nom) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE LOWER(p.nom) LIKE LOWER(:nom)", Personne.class)
                .setParameter("nom", "%" + nom.toLowerCase() + "%").getResultList();
    }

    public Personne rechercherPersonneParEmail(String email) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE p.email = :email", Personne.class)
                .setParameter("email", email).getSingleResult();
    }

    public List<Personne> rechercherPersonnesParPrenom(String prenom) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE LOWER(p.prenom) LIKE LOWER(:prenom)", Personne.class)
                .setParameter("prenom", "%" + prenom.toLowerCase() + "%").getResultList();
    }

    public List<Personne> rechercherPersonnesParSiteWeb(String siteWeb) {
        return entityManager.createQuery("SELECT p FROM Personne p WHERE LOWER(p.siteWeb) LIKE LOWER(:siteWeb)", Personne.class)
                .setParameter("siteWeb", siteWeb).getResultList();
    }

    public boolean authentifier(String email, String password) {
        Personne personne = entityManager.createQuery("SELECT p FROM Personne p WHERE p.email = :email", Personne.class)
                .setParameter("email", email).getSingleResult();
        return personne != null && personne.getMotDePasse().equals(password);
    }

    // Ajouts
    public void ajouterPersonne(Personne personne) {
        entityManager.persist(personne);
    }

    // Suppressions
    public void supprimerPersonne(Personne personne) {
        entityManager.remove(personne);
    }

    // Modifications
    public void modifierPersonne(Personne personne) {
        entityManager.merge(personne);
    }


}