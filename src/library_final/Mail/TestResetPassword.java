package library_final.Mail;

import java.util.Scanner;
import library_final.Mail.MailService;
import library_final.config.DatabaseConnection;
import library_final.model.DAO.UserDAO;
import library_final.model.entity.User;

public class TestResetPassword {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MailService mailService = new MailService();
        UserDAO userDAO = new UserDAO(DatabaseConnection.getConnection());
        User user = null;

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1️⃣ Se connecter");
            System.out.println("2️⃣ Réinitialiser son mot de passe");
            System.out.println("3️⃣ Quitter");
            System.out.print("👉 Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Vider le buffer

            switch (choice) {
                case 1: // Connexion
                    System.out.print("Email : ");
                    String email = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String password = scanner.nextLine();

                    user = userDAO.login(email, password);
                    if (user != null) {
                        System.out.println("Bienvenue, " + user.getName() + " !");
                    }
                    break;

                case 2: // Réinitialisation du mot de passe
                    System.out.print(" Entrez votre email : ");
                    String resetEmail = scanner.nextLine();

                    if (mailService.sendResetCode(resetEmail)) {
                        System.out.println("Code envoyé ! Vérifiez votre email.");
                        System.out.print("Entrez le code reçu : ");
                        String code = scanner.nextLine();

                        if (userDAO.verifyResetCode(resetEmail, code)) {
                            System.out.print("Entrez votre nouveau mot de passe : ");
                            String newPassword = scanner.nextLine();

                            if (userDAO.updatePassword(resetEmail, newPassword)) {
                                System.out.println("Mot de passe mis à jour avec succès !");
                            } else {
                                  System.out.println("Erreur lors de la mise à jour du mot de passe.");
                            }
                        } else {
                            System.out.println("Code incorrect !");
                        }
                    } else {
                        System.out.println("Échec de l'envoi du code. Vérifiez votre email.");
                    }
                    break;

                case 3: // Quitter
                    if (user != null) {
                        userDAO.logout(user);
                    }
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Option invalide. Essayez encore !");
            }
        }
    }
}
