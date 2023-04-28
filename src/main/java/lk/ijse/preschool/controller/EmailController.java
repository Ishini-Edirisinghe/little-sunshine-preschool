package lk.ijse.preschool.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailController {
    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");

        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enabled", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        String myAccountEmail = "chathurapreschooltalalla@gmail.com";
        String password = "hvmniivegylhkijx";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail,recepient);
        Transport.send(message);
        System.out.println("Message sent successfully!");
    }

    private static Message prepareMessage(Session session,String myAccountEmail,String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
            message.setSubject("Welcome to the Chathura Preschool System");
            message.setText("We are glad to have you on board with the Chathura Preschool System.\nIf you have any questions or concerns, please feel free to reach out to us.\n\nBest regards,\nIshini Edirisinghe");
            return message;
        } catch (Exception ex) {
            System.out.println(EmailController.class.getName());
        }
        return null;
    }

}
