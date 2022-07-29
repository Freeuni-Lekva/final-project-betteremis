package Helper;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static Helper.TokenGenerator.generateToken;
import static Helper.secret.Creditentials.*;

public class EmailSender {

    public static String sendEmail(String to) {
        String from = EMAIL;
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(EMAIL, PASSWORD);

            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Password Reset (Emis)");
            String url = "http://localhost:8080/BetterEmis_war_exploded/change-password/";
            String token = generateToken();
            url += token;
            String msg = "To reset your BetterEmis password, click here:\n"+
                    "<a href=\"" + url +"\"> Reset Password </a>";
            message.setContent(msg, "text/html; charset=utf-8");
            Transport.send(message);
            return token;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return null;
    }

}