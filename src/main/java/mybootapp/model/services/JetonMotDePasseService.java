package mybootapp.model.services;

import mybootapp.model.IDirectoryDAO;
import mybootapp.model.objects.JetonMotDePasse;
import mybootapp.model.objects.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class JetonMotDePasseService {

    @Autowired
    private IDirectoryDAO directoryDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment env;

    public JetonMotDePasse creerJeton(Personne personne) {
        JetonMotDePasse jetonMotDePasse = new JetonMotDePasse(personne);
        directoryDAO.ajouterJeton(jetonMotDePasse);
        return jetonMotDePasse;
    }

    public void envoyerEmail(JetonMotDePasse jetonMotDePasse) {
        String to = jetonMotDePasse.getPersonne().getEmail();
        String subject = "Réinitialisation de votre mot de passe";
        String baseUrl = env.getProperty("app.base-url");
        String content = "Bonjour " + jetonMotDePasse.getPersonne().getPrenom() + " " + jetonMotDePasse.getPersonne().getNom() + ",\n\n"
                + "Vous avez demandé à réinitialiser votre mot de passe.\n"
                + "Pour ce faire, veuillez cliquer sur le lien suivant : \n"
                + baseUrl + "/motdepasse/" + jetonMotDePasse.getToken() + "\n\n"
                + "Cordialement,\n"
                + "L'équipe d'Evan SEDENO et Guillaume RISCH";

        emailService.envoyerEmail(to, subject, content, env.getProperty("app.email-expediteur"));
    }

}