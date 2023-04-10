package mybootapp.model.objects;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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
    private String motDePasse;
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
        this.motDePasse = motDePasse;
    }
}