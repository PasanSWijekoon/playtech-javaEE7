package model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private static final String APP_EMAIL = "pasanwijekoon1@gmail.com";
    private static final String APP_PASSWORD = "rtfo cbdn xmdj knfn";
    private static final String APP_NAME = "Shop@PlayTech";

    // Method to send an email with support for both plain text and HTML
    public static void sendMail(String email, String subject, String content, boolean isHtml) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Mail.APP_EMAIL, Mail.APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            
            // Set sender name and email
            message.setFrom(new InternetAddress(Mail.APP_EMAIL,Mail.APP_NAME));
            
            // Set recipient
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            
            // Set email subject
            message.setSubject(subject);
            
            // Check if the content is HTML or plain text
            if (isHtml) {
                // Send as HTML
                message.setContent(content, "text/html");
            } else {
                // Send as plain text
                message.setText(content);
            }

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
