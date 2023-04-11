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
}
