/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fredi
 */

import library_final.model.DAO.LoanDAO;
import library_final.model.entity.Loan;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class LoanDAOTest {
    private static final LoanDAO loanDAO = new LoanDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== MENU DE GESTION DES EMPRUNTS =====");
            System.out.println("1. Ajouter un emprunt");
            System.out.println("2. Rechercher un emprunt par ID");
            System.out.println("3. Afficher tous les emprunts");
            System.out.println("4. Enregistrer un retour d'emprunt");
            System.out.println("5. Afficher le nombre total d'emprunts");
            System.out.println("6. Afficher les 5 livres les plus empruntés");
            System.out.println("7. Afficher le taux de retour à temps");
            System.out.println("8. Afficher le taux d'utilisation des exemplaires");
            System.out.println("9. Afficher l'utilisateur ayant emprunté le plus de livres");
            System.out.println("10. Quitter");
            System.out.print("Choisissez une option : ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne restante

            switch (choice) {
                case 1:
                    addLoan();
                    break;
                case 2:
                    findLoanById();
                    break;
                case 3:
                    findAllLoans();
                    break;
                case 4:
                    updateLoan();
                    break;
                case 5:
                    System.out.println("Total des emprunts : " + loanDAO.getTotalLoans());
                    break;
                case 6:
                    displayTopBorrowedBooks();
                    break;
                case 7:
                    System.out.println("Taux de retour à temps : " + loanDAO.getOnTimeReturnRate() + "%");
                    break;
                case 8:
                    System.out.println("Taux d'utilisation des exemplaires : " + loanDAO.getCopyUsageRate() + "%");
                    break;
                case 9:
                    System.out.println("Meilleur emprunteur : " + loanDAO.getTopBorrower());
                    break;
                case 10:
                    System.out.println("Fermeture du programme...");
                    break;
                default:
                    System.out.println("Option invalide, veuillez réessayer.");
            }
        } while (choice != 10);
    }

    private static void addLoan() {
        System.out.print("ID de l'emprunteur : ");
        int borrowerId = scanner.nextInt();
        System.out.print("ID de l'exemplaire : ");
        int copyId = scanner.nextInt();
        System.out.print("ID du bibliothécaire : ");
        int librarianId = scanner.nextInt();
        System.out.print("Date d'emprunt (YYYY-MM-DD) : ");
        Date dateLoan = Date.valueOf(scanner.next());
        System.out.print("Date retour prévue (YYYY-MM-DD) : ");
        Date expReturnDate = Date.valueOf(scanner.next());

        Loan loan = new Loan(0, copyId, borrowerId, librarianId, dateLoan, expReturnDate, null);
        if (loanDAO.create(loan)) {
            System.out.println("Emprunt ajouté avec succès.");
        } else {
            System.out.println("Échec de l'ajout de l'emprunt.");
        }
    }

    private static void findLoanById() {
        System.out.print("ID de l'emprunt : ");
        int loanId = scanner.nextInt();
        Loan loan = loanDAO.findById(loanId);
        if (loan != null) {
            System.out.println("Emprunt trouvé : " + loan);
        } else {
            System.out.println("Aucun emprunt trouvé avec cet ID.");
        }
    }

    private static void findAllLoans() {
        List<Loan> loans = loanDAO.findAll();
        if (loans.isEmpty()) {
            System.out.println("Aucun emprunt enregistré.");
        } else {
            for (Loan loan : loans) {
                System.out.println(loan);
            }
        }
    }

    private static void updateLoan() {
        System.out.print("ID de l'emprunt à mettre à jour : ");
        int loanId = scanner.nextInt();
        System.out.print("Date de retour effective (YYYY-MM-DD) : ");
        Date actReturnDate = Date.valueOf(scanner.next());

        Loan loan = loanDAO.findById(loanId);
        if (loan != null) {
            loan.setActReturnDate(actReturnDate);
            if (loanDAO.update(loan)) {
                System.out.println("Retour d'emprunt enregistré avec succès.");
            } else {
                System.out.println("Échec de la mise à jour.");
            }
        } else {
            System.out.println("Emprunt introuvable.");
        }
    }

    private static void displayTopBorrowedBooks() {
        List<String> topBooks = loanDAO.getTop5BorrowedBooks();
        if (topBooks.isEmpty()) {
            System.out.println("Aucun livre emprunté.");
        } else {
            System.out.println("Top 5 des livres les plus empruntés :");
            for (String book : topBooks) {
                System.out.println("- " + book);
            }
        }
    }
}
