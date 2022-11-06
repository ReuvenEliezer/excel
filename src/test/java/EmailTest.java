import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import emtities.EmailUtils;
import emtities.Email;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailTest {

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);

    @After
    public void tearDown(){
        greenMail.stop();
    }

    @Test
    public void simpleEmailTest() throws AddressException {
        Email email = new Email();
        email.setFrom("eliezer884@gmail.com");
        email.setTo(InternetAddress.parse("moriyamiz@gmail.com"));
        email.setSubject("subject");
        email.setText("body");
        EmailUtils.sendMessage(email,true,createSession());
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();

    }

    private static Session createSession() {
        String host = "localhost";
        Integer port = 3025;
//        String username = "username"; //eliezer884 without @gmail.com
//        String pass = "password";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
//        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.user", username);
//        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", port);
//        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties);//
//                , new Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, pass);
//                    }
//                });
    }
}
