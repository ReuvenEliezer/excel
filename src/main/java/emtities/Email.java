package emtities;

import javax.mail.internet.InternetAddress;
import java.util.Arrays;
import java.util.List;

public class Email {

    private String from;
    private InternetAddress[] to;
    private InternetAddress[] cc;
    private InternetAddress[] bcc;
    private String subject;
    private String text ="";
    private List<EmailAttachment> attachmentList;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public InternetAddress[] getTo() {
        return to;
    }

    public void setTo(InternetAddress[] to) {
        this.to = to;
    }

    public InternetAddress[] getCc() {
        return cc;
    }

    public void setCc(InternetAddress[] cc) {
        this.cc = cc;
    }

    public InternetAddress[] getBcc() {
        return bcc;
    }

    public void setBcc(InternetAddress[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<EmailAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<EmailAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                ", to=" + Arrays.toString(to) +
                ", cc=" + Arrays.toString(cc) +
                ", bcc=" + Arrays.toString(bcc) +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", attachmentList=" + attachmentList +
                '}';
    }
}
