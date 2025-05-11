/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.Mail;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.security.SecureRandom;
import java.util.Properties;
import library_final.config.ConfigLoader;
import library_final.config.DatabaseConnection;
import library_final.model.DAO.UserDAO;
import library_final.model.entity.User;

/**
 *
 * @author fredi
 */
public class MailService {
    
    /**
     * Envoi du code de réinitialisation
     * @param email
     * @return 
     */
    public boolean sendResetCode(String email) {
       UserDAO userDAO = new UserDAO(DatabaseConnection.getConnection());

       // Vérifier si l'utilisateur existe avant de générer le code
       User user = userDAO.findByEmail(email);
       if (user == null) {
           System.err.println("Aucun utilisateur trouvé avec cet email : " + email);
           return false;
       }

       // Générer et enregistrer le code
       boolean isUpdated = userDAO.saveResetCode(email);

       if (isUpdated) {
           // Récupérer à nouveau l'utilisateur pour obtenir le code mis à jour
           user = userDAO.findByEmail(email);
           String code = user.getResetCode();

           if (code == null) {
               System.err.println("Le code est toujours NULL après la mise à jour !");
               return false;
           }

           MailService mailService = new MailService();
           mailService.sendVerificationCode(email, code);
           return true;
       } else {
           System.err.println("Échec de l'enregistrement du code en base !");
       }
       return false;
   }



    
    /**
     * Envoi d'un mail avec le code
     * @param recipientEmail
     * @param code 
     */
    public void sendVerificationCode(String recipientEmail, String code){
        String subject = "Votre code de réinitialisation";
        String message = "Votre code de réinintialisation est: " + code + "\nSi vous n'avez pas demandé de code, ignorez ce mail";
        
        sendMail(recipientEmail, subject, message);
    }
    
    /**
     * Configuration de l'envoi d'un mail
     * @param recipient
     * @param subject
     * @param content 
     */
    public static void sendMail(String recipient, String subject, String content){
        String username = ConfigLoader.getProperty("mail.username");
        String password = ConfigLoader.getProperty("mail.password");
        
        Properties props = new Properties();
        props.put("mail.smtp.host", ConfigLoader.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", ConfigLoader.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", ConfigLoader.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", ConfigLoader.getProperty("mail.smtp.starttls.enable"));
       
        Session session = Session.getInstance(props, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, password);
            }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);
            
            Transport.send(message);
            System.out.println("Email envoyé avec succès à: "+ recipient);  
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur: Echec d'envoi");  
        }  
    }  

    private static final int CODE_LENGTH = 6;
    
    /**
     * Génération d'un code à 6 chiffres
     * @return 
     */
    public static String generateVerificationCode(){
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        
        for(int i = 0; i < CODE_LENGTH; i++){
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

}
