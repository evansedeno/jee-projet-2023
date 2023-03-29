package mybootapp.model;

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
    private Date dateDeNaissance;
    private String motdepasse;
    @ManyToOne
    @JoinColumn(name="groupe_id")
    private Groupe groupe;

    public Personne() {
    }

    public Personne(String nom, String prenom, String email, String siteWeb, Date dateDeNaissance, Groupe groupe, String motdepasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.siteWeb = siteWeb;
        this.dateDeNaissance = dateDeNaissance;
        this.groupe = groupe;
        this.motdepasse = motdepasse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateNaissance) {
        this.dateDeNaissance = dateNaissance;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public String getMotDePasse() {
        return motdepasse;
    }

    public void setMotDePasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}