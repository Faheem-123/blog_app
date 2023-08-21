package blog_App.service;

import blog_App.payloads.EmailResponse;
import blog_App.utils.EmailUtil;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Properties;

@Service
public class EmailService {
    @Value("${email.user}")
    private String user;
    @Value("${email.password}")
    private String pass;
    @Value("${email.host}")
    private String host;
    @Value("${email.protocol}")
    private String protocol;
    @Value("${email.auth}")
    private String auth;
    @Value("${email.socketFactoryPort}")
    private String socketFactoryPort;
    @Value("${email.timeout}")
    private String timeout;
    @Value("${email.checkServerIdentity}")
    private String checkServerIdentity;
    @Value("${email.trust}")
    private String trust;
    @Value("${email.connectionTimeout}")
    private String connectionTimeout;
    @Value("${email.debug}")
    private String debug;
    @Value("${email.fallback}")
    private String fallback;

    @Autowired
    private EmailUtil emailUtil;

    public EmailResponse sentEmail(String to, String subject, String body) {
        return emailUtil.sentEmail(to, subject, body);
    }

    public EmailResponse sentEmailWithAttachment(String to, String subject, String body, MultipartFile file, String cc) {
        return emailUtil.sentEmailWithAttachment(to, subject, body, file, cc);
    }

    public boolean sendEmail(String subject, String content, String to){
        boolean f=false;
        Properties props = getProperties();
        Authenticator auth = authenticator();
        Session session = Session.getInstance(props, auth);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
//            mimeMessage.setFrom(from);
            mimeMessage.setFrom(new InternetAddress(user));
            mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(content);
            Transport.send(mimeMessage);
            System.out.println("message sent"+ mimeMessage);
            f=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }


    private Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");   //Enable TLS
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.ssl.trust", trust);
        return props;
    }

    private Authenticator authenticator() {
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        };
        return auth;
    }
}
