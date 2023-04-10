package mybootapp.model.services;

import org.mindrot.jbcrypt.BCrypt;

public class MotDePasseService {

    public static String crypterMotDePasse(String motDePasse) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(motDePasse, salt);
    }

    public static boolean comparerMotDePasse(String motDePasse, String motDePasseCrypte) {
        return BCrypt.checkpw(motDePasse, motDePasseCrypte);
    }
}
