package blog_App.utils;

import blog_App.payloads.EmailResponse;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;


@Component
public class EmailUtil {

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


    public EmailResponse sentEmail(String recipient, String subject, String content) {
        EmailResponse cfsResponseVO = null;
        try {
            Properties props = getProperties();
            Authenticator auth = authenticator();

            Session session = Session.getInstance(props, auth);

            ArrayList<String> recipientsArray = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(recipient, ";");

            while (st.hasMoreTokens()) {
                recipientsArray.add(st.nextToken());
            }

            // create a new MimeMessage object (using the Session created above)
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            message.setSubject(subject);


            message.setContent(content, "text/html");

            int sizeTo = recipientsArray.size();
            // System.out.println("address to ");
            InternetAddress[] addressTo = new InternetAddress[sizeTo];
            for (int i = 0; i < sizeTo; i++) {
                addressTo[i] = new InternetAddress(recipientsArray.get(i));
            }

            message.setRecipients(Message.RecipientType.TO, addressTo);

            Transport.send(message);
            System.out.println("sent email successfully");
            cfsResponseVO = EmailResponse.builder()
                    .code(00)
                    .message("sent email successfully")
                    .build();

        } catch (Exception e) {
            System.out.println("Error in Email Sending");
            e.printStackTrace();
            cfsResponseVO = EmailResponse.builder()
                    .code(01)
                    .message(e.getMessage())
                    .build();
        }

        return cfsResponseVO;

    }

    private Properties getProperties() {

        Properties props = new Properties();


        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");   //Enable TLS
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.ssl.trust", trust);



/*
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.socketFactory.port", socketFactoryPort);
        props.put("mail.smtp.port", socketFactoryPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //Enable SSL
        props.put("mail.smtp.timeout", timeout);
        props.put("mail.smtp.ssl.checkserveridentity", checkServerIdentity);
        props.put("mail.smtp.connectiontimeout", connectionTimeout);
        props.put("mail.smtp.debug", debug);
        props.put("mail.smtp.socketFactory.fallback", fallback);
*/
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

    public EmailResponse sentEmailWithAttachment(String recipient, String subject, String content, MultipartFile files, String cc) {
        EmailResponse cfsResponseVO = null;


        try {
            Properties props = getProperties();
            Authenticator auth = authenticator();

            Session session = Session.getInstance(props, auth);

            ArrayList<String> recipientsArray = new ArrayList<String>();
            StringTokenizer st = new StringTokenizer(recipient, ";");

            while (st.hasMoreTokens()) {
                recipientsArray.add(st.nextToken());
            }


            // create a new MimeMessage object (using the Session created above)
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setSubject(subject);

            //create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            //set the actual message
            messageBodyPart.setContent(content, "text/html");
            //create an instance of multipart object
            Multipart multipart = new MimeMultipart();
            //set the first text message part
            multipart.addBodyPart(messageBodyPart);

            //set the second part, which is the attachment
            messageBodyPart = new MimeBodyPart();


            DataSource source = new ByteArrayDataSource(files.getBytes(), files.getContentType());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(files.getOriginalFilename());
            multipart.addBodyPart(messageBodyPart);

            //send the entire message parts
            message.setContent(multipart);


            int sizeTo = recipientsArray.size();
            // System.out.println("address to ");
            InternetAddress[] addressTo = new InternetAddress[sizeTo];
            for (int i = 0; i < sizeTo; i++) {
                addressTo[i] = new InternetAddress(recipientsArray.get(i));
            }

            message.setRecipients(Message.RecipientType.TO, addressTo);


            ArrayList<String> ccArray = new ArrayList<String>();
            StringTokenizer ccSt = new StringTokenizer(cc, ";");

            while (ccSt.hasMoreTokens()) {
                ccArray.add(ccSt.nextToken());
            }


            InternetAddress[] addressCC = new InternetAddress[ccArray.size()];

            for (int i = 0; i < addressCC.length; i++) {
                addressCC[i] = new InternetAddress(ccArray.get(i));
            }

            message.setRecipients(Message.RecipientType.CC, addressCC);
            // System.out.println("recipent set");
            Transport.send(message);
            System.out.println("sent email successfully");
            cfsResponseVO = EmailResponse.builder()
                    .code(00)
                    .message("sent email successfully")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            cfsResponseVO = EmailResponse.builder()
                    .code(01)
                    .message(e.getMessage())
                    .build();
        }
        return cfsResponseVO;

    }

}
