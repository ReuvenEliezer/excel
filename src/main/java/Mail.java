import emtities.ContentTypeEnum;
import emtities.Email;
import emtities.EmailAttachment;
import emtities.EmailUtils;
import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Mail {

    public static void main(String[] args) throws Exception {
        Email email = new Email();
        email.setFrom("yairreuven10@gmail.com");
        email.setTo(InternetAddress.parse("eliezer884@gmail.com"));
        email.setSubject("subject");
        email.setText("body");
        List<EmailAttachment> attachmentList = new ArrayList<>();
        byte[] bytes = FileUtils.readFileToByteArray(new File(getFilePath() + "provawrite.xlsx"));
        DataSource dataSource = new ByteArrayDataSource(bytes, ContentTypeEnum.EXCEL.getValue());
        EmailAttachment emailAttachment = new EmailAttachment(new DataHandler(dataSource),"excel.xlsx");
        attachmentList.add(emailAttachment);
        email.setAttachmentList(attachmentList);
        EmailUtils.sendMessage(email, true, null);
    }

    private static String getFilePath() {
        return Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    }

    private static Session createSession() {
        String host = "127.0.0.1";
        Integer port = 3025;
//        String username = "username"; //eliezer884 without @gmail.com
//        String pass = "password";
        // Get system properties
        Properties properties = new Properties();
        // Setup mail server
//        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.user", username);
//        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", port);
//        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties);//
    }
}
