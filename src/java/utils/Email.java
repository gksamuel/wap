/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Content;
import entities.Lostpassword;
import entities.Registration;
import entities.Seekers;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

/**
 *
 * @author gachanja
 */
public class Email {

    Properties props = new Properties();
    String fromAddress = "customerservice@myjob.co.ke";
    String fromName = "MyJob.co.ke";
    InternetAddress ia = new InternetAddress();

    public Email() {
        try {
//            props.put("mail.smtp.host", "localhost");
            props.put("mail.smtp.host", "myjob.co.ke");
            props.put("mail.smtp.port", "25");
            ia.setAddress(fromAddress);
            ia.setPersonal(fromName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String sendRegistrationEmail(Registration registration, Content content) {
        try {
            Logger.getLogger(Email.class.getName()).log(Level.INFO, null, "Sending email to " + registration.getEmail());
            Session session = Session.getInstance(props);
            Message message = new MimeMessage(session);
            message.setFrom(ia);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(registration.getEmail()));
            message.setSubject("myjob.co.ke Registration");
            String messageContent = content.getContent().replace("UUUUU", registration.getUsername()).replace("DDDDD/faces", "myjob.co.ke/m").replace("/Confirm.xhtml?", "?registerActions=completeRegistration&").replace("CCCCC", registration.getCode()).replace("asdewedhyfse", "code");
            message.setDataHandler(new DataHandler(new ByteArrayDataSource(messageContent, "text/html")));
            Transport.send(message);
            Logger.getLogger(Email.class.getName()).log(Level.INFO, "Sent email to {0}", registration.getEmail());
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            return ("failed");
        }
        return ("Done");
    }

    public String sendPasswordEmail(Lostpassword lostpassword, String toAddress) {
        try {
            Session session = Session.getInstance(props);
            Message message = new MimeMessage(session);
            message.setFrom(ia);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject("myjob.co.ke - Password Recovery");
            message.setText("Hi " + lostpassword.getUsername() + "\nPlease go to the url https://myjob.co.ke/m/registration/resetPassword.jsp?asdewedhyfse=" + lostpassword.getCode() + " to reset your password");
            Transport.send(message);
        } catch (MessagingException e) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
        }
        return ("Done");
    }

    public String sendEmailValidation(Seekers seeker) {
        try {
            Session session = Session.getInstance(props);
            Message message = new MimeMessage(session);
            message.setFrom(ia);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(seeker.getEmail()));
            message.setSubject("myjob.co.ke - Email Validation");
            message.setText("Hi " + seeker.getUsername() + "\nPlease go to the url https://myjob.co.ke/m/profile/validateEmail.jsp?id=" + seeker.getId() + " to validate your email");
            Transport.send(message);
        } catch (MessagingException e) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, e);
        }
        return ("Done");
    }
}
