package mybootapp.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Service
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void envoyerEmail(String destinataire, String objet, String message, String expediteur) {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail);

        try {
            helper.setTo(destinataire);
            helper.setSubject(objet);
            helper.setText(message);
            helper.setFrom(Objects.requireNonNull(mailSender.getUsername()), expediteur);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(mail);
    }
}