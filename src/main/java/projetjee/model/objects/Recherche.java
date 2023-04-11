package projetjee.model.objects;

import lombok.Data;
import projetjee.model.IDirectoryDAO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class Recherche {

    private String id;
    private String nom;
    private String prenom;
    private String siteWeb;
    private String groupe;
    private IDirectoryDAO directoryDAO;


    /* ------------------ CONSTRUCTEURS ------------------*/
    public Recherche() {
    }

    public Recherche(String id, String nom, String prenom, String siteWeb, String groupe, IDirectoryDAO directoryDAO) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.siteWeb = siteWeb;
        this.groupe = groupe;
        this.directoryDAO = directoryDAO;
    }


    /* ------------------ METHODES ------------------*/
    public List<Personne> rechercherPersonnes() {
        List<Personne> personnes = new ArrayList<>();

        if (id == null && nom == null && prenom == null && siteWeb == null && groupe == null) {
            return personnes;
        }

        if (id != null && !id.isEmpty() && Long.parseLong(id) > 0) {
            personnes.add(directoryDAO.rechercherPersonneParId(Long.parseLong(id)));
        } else {
            Groupe groupe1 = (groupe != null && !groupe.isEmpty()) ? directoryDAO.rechercherGroupeParId(Long.parseLong(groupe)) : null;
            if (groupe1 != null) {
                personnes = directoryDAO.rechercherPersonnesParGroupe(groupe1);
            }
            if (nom != null && !nom.isEmpty()) {
                personnes = (personnes.isEmpty()) ? directoryDAO.rechercherPersonnesParNom(nom) : personnes.stream().filter(p -> p.getNom().equals(nom)).collect(Collectors.toList());
            }
            if (prenom != null && !prenom.isEmpty()) {
                personnes = (personnes.isEmpty()) ? directoryDAO.rechercherPersonnesParPrenom(prenom) : personnes.stream().filter(p -> p.getPrenom().equals(prenom)).collect(Collectors.toList());
            }
            if (siteWeb != null && !siteWeb.isEmpty()) {
                personnes = (personnes.isEmpty()) ? directoryDAO.rechercherPersonnesParSiteWeb(siteWeb) : personnes.stream().filter(p -> p.getSiteWeb().equals(siteWeb)).collect(Collectors.toList());
            }
        }

        return personnes;
    }
}
