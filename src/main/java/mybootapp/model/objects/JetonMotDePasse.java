package mybootapp.model.objects;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Component
public class JetonMotDePasse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private Date dateDeCreation;
    private Date dateDeExpiration;
    @ManyToOne
    @JoinColumn(name = "personne_id")
    private Personne personne;
    private String nouveauMotDePasse;
    private String confirmationMotDePasse;

    /* ----------------- CONSTRUCTEURS ----------------- */
    public JetonMotDePasse() {
    }

    public JetonMotDePasse(Personne personne) {
        this.token = genererToken();
        this.dateDeCreation = new Date();
        this.dateDeExpiration = new Date(this.dateDeCreation.getTime() + 1000 * 60 * 60 * 24);
        this.personne = personne;
        this.nouveauMotDePasse = "";
        this.confirmationMotDePasse = "";
    }

    /* ----------------- METHODES ----------------- */
    public boolean estValide() {
        return !this.dateDeExpiration.before(new Date());
    }

    public String genererToken() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }


}