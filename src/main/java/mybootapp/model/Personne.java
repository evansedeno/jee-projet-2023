package mybootapp.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String siteWeb;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateDeNaissance;
    private String motdepasse;
    @ManyToOne
    @JoinColumn(name = "groupe_id")
    private Groupe groupe;


    /* ----------------- CONSTRUCTEURS ----------------- */
    public Personne() {
    }

    public Personne(String nom, String prenom, String email, String siteWeb, Date dateDeNaissance, Groupe groupe, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.siteWeb = siteWeb;
        this.dateDeNaissance = dateDeNaissance;
        this.groupe = groupe;
        this.motdepasse = motDePasse;
    }


    /* ----------------- GETTERS ----------------- */

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public String getMotDePasse() {
        return motdepasse;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }


    /* ----------------- SETTERS ----------------- */

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void setMotDePasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public void setDateDeNaissance(Date dateNaissance) {
        this.dateDeNaissance = dateNaissance;
    }
}