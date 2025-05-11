/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fredi
 */

import library_final.model.entity.Book;
import java.util.List;
import library_final.model.DAO.BookDAO;

/**
 * Classe de test pour BookDAO
 * @author fredi
 */
public class BookDAOTest {
    
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        System.out.println("===== TEST : AJOUT D'UN LIVRE =====");
        Book newBook = new Book(0, "JavaFX for Beginners", "John Doe", "978-1234567890", 2024, null, "A complete guide to JavaFX", "A1");
        boolean isAdded = bookDAO.create(newBook);
        if (isAdded) {
            System.out.println("Livre ajouté avec succès ! ID : " + newBook.getIdBook());
        } else {
            System.out.println("Échec de l'ajout du livre.");
        }

        System.out.println("\n===== TEST : RECHERCHE D'UN LIVRE PAR ID =====");
        Book foundBook = bookDAO.findById(newBook.getIdBook());
        if (foundBook != null) {
            System.out.println("Livre trouvé : " + foundBook.getTitle() + " | Auteur : " + foundBook.getAuthor());
        } else {
            System.out.println("Aucun livre trouvé avec cet ID.");
        }

        System.out.println("\n===== TEST : AFFICHAGE DE TOUS LES LIVRES =====");
        List<Book> books = bookDAO.findAll();
        if (!books.isEmpty()) {
            for (Book book : books) {
                System.out.println("ID: " + book.getIdBook() + " | Titre: " + book.getTitle());
            }
        } else {
            System.out.println("Aucun livre actif trouvé.");
        }

        System.out.println("\n===== TEST : MISE À JOUR D'UN LIVRE =====");
        if (foundBook != null) {
            foundBook.setTitle("JavaFX Advanced");
            boolean isUpdated = bookDAO.update(foundBook);
            if (isUpdated) {
                System.out.println("Livre mis à jour avec succès !");
            } else {
                System.out.println("Échec de la mise à jour.");
            }
        }

        System.out.println("\n===== TEST : SUPPRESSION LOGIQUE D'UN LIVRE =====");
        boolean isDeleted = bookDAO.delete(newBook);
        if (isDeleted) {
            System.out.println("Livre supprimé (suppression logique).");
        } else {
            System.out.println("Échec de la suppression.");
        }

        System.out.println("\n===== TEST : VÉRIFICATION DE LA SUPPRESSION =====");
        System.out.println(newBook.getIdBook());
        Book deletedBook = bookDAO.findById(newBook.getIdBook());
        if (deletedBook == null) {
            System.out.println("Le livre a bien été supprimé.");
        } else {
            System.out.println("Le livre est toujours actif !");
        }
    }
}
