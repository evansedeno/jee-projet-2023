package mybootapp.model.objects;

import lombok.Data;

@Data
public class ChangementMotDePasse {
    private String ancienMotDePasse;
    private String nouveauMotDePasse;
    private String confirmationMotDePasse;
    private Personne personne;

    /* ----------------- CONSTRUCTEURS ----------------- */
    public ChangementMotDePasse() {
    }

    public ChangementMotDePasse(String ancienMotDePasse, String nouveauMotDePasse, String confirmationMotDePasse, Personne personne) {
        this.ancienMotDePasse = ancienMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
        this.confirmationMotDePasse = confirmationMotDePasse;
        this.personne = personne;
    }
}
