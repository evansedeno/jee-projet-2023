package projetjee.model.objects;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class Utilisateur {

    private Personne personne;

    /* ------------------ CONSTRUCTEURS ------------------*/
    public Utilisateur() {
        personne = null;
    }
}