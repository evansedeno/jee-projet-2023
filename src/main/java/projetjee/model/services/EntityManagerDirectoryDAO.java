package projetjee.model.services;

import projetjee.model.IDirectoryDAO;
import projetjee.model.objects.Groupe;
import projetjee.model.objects.JetonMotDePasse;
import projetjee.model.objects.Personne;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    // Ajouts
    public void ajouterGroupe(Groupe groupe) {
        entityManager.persist(groupe);
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
        List<Personne> personnes = rechercherToutesLesPersonnes();
        if (personnes.isEmpty()) {
            return null;
        }
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

    public Personne authentifier(String email, String motDePasse) {
        Personne personne = null;
        try {
            personne = entityManager.createQuery("SELECT p FROM Personne p WHERE p.email = :email", Personne.class)
                    .setParameter("email", email)
                    .getSingleResult();
            if (!MotDePasseService.comparerMotDePasse(motDePasse, personne.getMotDePasse())) {
                personne = null;
            }
        } catch (NoResultException ignored) {
        }
        return personne;
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


    /* ----------------- JetonMotDePasse ----------------- */
    public List<JetonMotDePasse> rechercherTousLesJetons() {
        return entityManager.createQuery("SELECT j FROM JetonMotDePasse j", JetonMotDePasse.class).getResultList();
    }

    public void supprimerJeton(JetonMotDePasse jeton) {
        entityManager.remove(jeton);
    }

    public void ajouterJeton(JetonMotDePasse jeton) {
        entityManager.persist(jeton);
    }

    public JetonMotDePasse rechercherJetonParToken(String token) {
        JetonMotDePasse jeton = null;
        try {
            jeton = entityManager.createQuery("SELECT j FROM JetonMotDePasse j WHERE j.token = :token", JetonMotDePasse.class)
                    .setParameter("token", token).getSingleResult();
        } catch (NoResultException ignored) {
        }
        return jeton;
    }
}