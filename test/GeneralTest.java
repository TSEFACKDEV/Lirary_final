
import java.util.Scanner;
import library_final.config.DatabaseConnection;
import library_final.model.DAO.UserDAO;
import library_final.model.entity.User;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import library_final.Mail.MailService;
import static library_final.config.DatabaseConnection.connection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fredi
 */
public class GeneralTest {
    
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        User user = null;
        
        System.out.println("Veuillez vous authentifier");
        
        System.out.println("-------------------------MENU--------------------------------");
        System.out.println("1. Se connecter");
        System.out.println("2. Changer de mot de passe");
        int choice = sc.nextInt();
        sc.nextLine();
        switch(choice){
        case 1: //Connection
            System.out.print("Votre adresse email: ");
            String email = sc.nextLine();
            System.out.print("Votre mot de passe: ");
            String password = sc.nextLine();
            connect(user, email, password);
            break;
            
        case 2: //Change Password
            System.out.print("Votre adresse email: ");
            String emailReset = sc.nextLine();
            resetPassword(emailReset);
            
        }
    }
    
    public static void resetPassword(String email){
        Scanner sc = new Scanner(System.in);
        MailService mailService = new MailService();
        UserDAO userDAO = new UserDAO(DatabaseConnection.getConnection());
        if(mailService.sendResetCode(email)){
            System.out.println("Entrez le code reçu");
            String newPassword = sc.nextLine();

            if(userDAO.verifyResetCode(email, newPassword)){
                System.out.print("Entrez le nouveau mot de passe");
                String passwordRset = sc.nextLine();

                if(userDAO.updatePassword(email, newPassword)){
                    System.out.println("Mot de passe mis à jour avec succès !");
                    } else {
                          System.out.println("Erreur lors de la mise à jour du mot de passe.");
                    }
                }else{
                    System.out.println("Mots de passe incompatibles");
                }
            }else{
                System.out.println("Erreur lors de l'envoi du mail de réinitialisation.");
            }
        }
    
    public static void connect(User user,String email, String password){
        UserDAO userDAO = new UserDAO(DatabaseConnection.getConnection());
        user = userDAO.login(email, password);
        System.out.println("Bienvenu "+user.getName());
        String sql = "SELECT role FROM user WHERE id = ?";
        String status = null;
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                status = rs.getString("role");
            }
            System.out.println(status);
            if("Librarian".equalsIgnoreCase(status)){
                System.out.println("Menu du bibliothécaire");
            }else{
                System.out.println("Menu de l'administrateur");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

