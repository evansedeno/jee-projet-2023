package mybootapp.model.objects;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Groupe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;

    public Groupe() {
    }

    public Groupe(String nom) {
        this.nom = nom;
    }
}