package emtities;

import emtities.Email;
import emtities.EmailAttachment;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class EmailUtils {

    public static void sendMessage(Email email, boolean isSync, Session session) {
        try {
            // Get the default Session object.
            if (session == null)
                session = createSession();//Session.getDefaultInstance(createSession());
            Message message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(email.getFrom()));
            message.setRecipients(Message.RecipientType.TO, email.getTo());
            if (email.getCc() != null)
                message.addRecipients(Message.RecipientType.CC, email.getCc());
            if (email.getBcc() != null)
                message.addRecipients(Message.RecipientType.BCC, email.getBcc());

            message.setSubject(email.getSubject());

            //create the message part
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(email.getText());
            multipart.addBodyPart(messageBodyPart);

            if (email.getAttachmentList() != null) {
                for (EmailAttachment attachment : email.getAttachmentList()) {
                    DataHandler dataHandler = attachment.getDataHandler();
                    if (dataHandler == null || dataHandler.getContentType() == null) {
                        System.out.println("missing bytes of file or attachment type");
                        //log error
                        continue;
                    }
                    String fileName = attachment.getFileName();
                    if (fileName == null) {
                        System.out.println("missing file name");
                        //log warning
                    }
                    BodyPart fileBodyPart = new MimeBodyPart();
                    fileBodyPart.setDataHandler(dataHandler);
                    fileBodyPart.setFileName(fileName);
                    multipart.addBodyPart(fileBodyPart);
                }
            }

            message.setContent(multipart);

            sendMessage(message, isSync);

            System.out.println("sent email successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private static Session createSession() {
        String host = "smtp.gmail.com";
        Integer port = 587;
        String username = "Yairreuven10"; //eliezer884 without @gmail.com
        String pass = "***";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.user", username);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, pass);
                    }
                });
    }

    private static void sendMessage(Message message, boolean isSync) throws MessagingException {
        if (isSync)
            Transport.send(message);
        else {
            CompletableFuture.runAsync(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
