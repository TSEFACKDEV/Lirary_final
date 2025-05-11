/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.DAO;

import java.security.SecureRandom;
import library_final.model.entity.User;
import java.sql.*;
import java.sql.ResultSet;
import static library_final.Mail.MailService.generateVerificationCode;
import library_final.model.entity.Administrator;
import library_final.model.entity.Librarian;

/**
 *
 * @author fredi
 */

public class UserDAO {
    
    private final Connection connection;
    
    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    
    /*******************************************************************************************************
     *                                              1. CONNECTION
    *******************************************************************************************************/
    
    /**
    * Vérifie les informations de connexion d'un utilisateur.
    * @param email
    * @param password
    * @return l'utilisateur s'il est authentifié, sinon null.
    */
   public User login(String email, String password) {
       String sql = "SELECT u.id, p.name, p.nic, p.phone, p.email, u.password, u.status, u.role " +
                    "FROM user u INNER JOIN person p ON u.id = p.id " +
                    "WHERE p.email = ? AND u.password = ?";

       try (PreparedStatement stmt = connection.prepareStatement(sql)) {
           stmt.setString(1, email);
           stmt.setString(2, password);
           ResultSet rs = stmt.executeQuery();
 
           if (rs.next()) {
               int id = rs.getInt("id");
               String name = rs.getString("name");
               String nic = rs.getString("nic");
               String phone = rs.getString("phone");
               String role = rs.getString("role");

               User user;
               if ("Administrator".equalsIgnoreCase(role)) {
                   user = new Administrator(id, name, nic, phone, email, password, "Active");
               } else {
                   user = new Librarian(id, name, nic, phone, email, password, "Active");
               }

               System.out.println("Connexion réussie pour : " + email);
               return user;
           } else {
               System.out.println("Email ou mot de passe incorrect.");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
   }
   
    /*******************************************************************************************************
     *                                              2. DECONNECTION
    *******************************************************************************************************/

   /**
    * Déconnecte un utilisateur (pour le moment, on affiche juste un message).
    * @param user
    */
   public void logout(User user) {
       if (user != null) {
           System.out.println("Déconnexion réussie pour : " + user.getEmail());
       }
   }

   
    /*******************************************************************************************************
     *                                        3. REINITIALISATION DU MOT DE PASSE
    *******************************************************************************************************/
    
   /**
    * Cherche un utilisateur dans la base de données et récupère ses informations
    * @param email
    * @return 
    */
    public User findByEmail(String email) {
        User user = null;
        String sql = "SELECT u.id, p.name, p.nic, p.phone, p.email, u.password, u.status, u.role, u.reset_code " +
                     "FROM user u " +
                     "INNER JOIN person p ON u.id = p.id " +
                     "WHERE p.email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String nic = rs.getString("nic");
                String phone = rs.getString("phone");
                String password = rs.getString("password");
                String status = rs.getString("status");
                String role = rs.getString("role");
                String resetCode = rs.getString("reset_code"); // Ajout du code

                System.out.println("Code trouvé dans la base pour " + email + " : " + resetCode); 

                if ("Administrator".equalsIgnoreCase(role)) {
                    user = new Administrator(id, name, nic, phone, email, password, status);
                } else if ("Librarian".equalsIgnoreCase(role)) {
                    user = new Librarian(id, name, nic, phone, email, password, status);
                }

                if (user != null) {
                    user.setResetCode(resetCode);
                }
            } else {
                System.err.println("Aucun utilisateur trouvé avec cet email !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Sauvegarde du code de réinitialisation en base de données
     * @param email
     * @return 
     */
    public boolean saveResetCode(String email) {
        String code = generateVerificationCode();

        // Vérifier d'abord si l'email existe
        String checkEmailQuery = "SELECT u.id FROM user u INNER JOIN person p ON u.id = p.id WHERE p.email = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkEmailQuery)) {
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {  
                System.err.println("ERREUR: L'email " + email + " n'existe pas en base !");
                return false;
            }

            int userId = rs.getInt("id");  // Récupérer l'ID de l'utilisateur

            // Maintenant, on met à jour le code de réinitialisation
            String updateQuery = "UPDATE user SET reset_code = ? WHERE id = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setString(1, code);
                updateStmt.setInt(2, userId);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Code enregistré en base pour " + email + " : " + code);
                    return true;
                } else {
                    System.err.println("Problème lors de la mise à jour du code !");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Récupère un utilisateur en fonction du code de réinitialisation
     * @param code
     * @return 
     */
    public User findByResetCode(String code) {
        User user = null;
        String sql = "SELECT u.*, p.email FROM user u INNER JOIN person p ON u.id = p.id WHERE u.reset_code = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String nic = rs.getString("nic");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String status = rs.getString("status");
                String role = rs.getString("role");

                if ("Administrator".equalsIgnoreCase(role)) {
                    user = new Administrator(id, name, nic, phone, email, password, status);
                } else if ("Librarian".equalsIgnoreCase(role)) {
                    user = new Librarian(id, name, nic, phone, email, password, status);
                }

                System.out.println("Utilisateur trouvé avec ce code: " + code);
            } else {
                System.err.println("Aucun utilisateur trouvé avec ce code.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * Vérification du code (retourne true si le code est valide)
     * @param email
     * @param code
     * @return 
     */
    public boolean verifyResetCode(String email, String code) {
        String sql = "SELECT COUNT(*) FROM user u JOIN person p ON u.id=p.id WHERE p.email = ? AND u.reset_code = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, code);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE id = (SELECT id FROM person WHERE email = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

