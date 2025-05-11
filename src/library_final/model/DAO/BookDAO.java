/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.DAO;

import library_final.model.entity.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import library_final.config.DatabaseConnection;


/**
 *
 * @author fredi
 */

public class BookDAO implements DAO<Book> {

    private final Connection connection;

    public BookDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Ajoute un nouveau livre dans la base de données
     * @param book Le livre à ajouter
     * @return true si l'ajout est réussi, false sinon
     */
    @Override
    public boolean create(Book book) {
        String sql = "INSERT INTO book (title, author, isbn, position, year_publication, image, description, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, 'Active')";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            if(findByIsbn(book.getIsbn())!=null){
                System.out.println("Un livre avec cet ISBN existe déjà");
            }
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getPosition());
            stmt.setInt(5, book.getYearPublication());
            stmt.setString(6, book.getImage());
            stmt.setString(7, book.getDescription());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    book.setIdBook(generatedKeys.getInt(1)); // Récupération de l'ID auto-généré
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
        return false;
    }

    /**
     * Récupère un livre par son ID
     * @param id L'identifiant du livre
     * @return Le livre correspondant ou null si introuvable
     */
    @Override
    public Book findById(int id) {
        String sql = "SELECT * FROM book WHERE id_book = ? AND status = 'Active'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("id_book"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year_publication"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getString("position")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du livre : " + e.getMessage());
        }
        return null;
    }
    
    public Book findByIsbn(String isbn) {
        String sql = "SELECT * FROM book WHERE isbn = ? AND status = 'Active'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("id_book"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year_publication"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getString("position")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du livre : " + e.getMessage());
        }
        return null;
    }

    /**
     * Récupère tous les livres actifs
     * @return Liste des livres
     */
    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book WHERE status = 'Active'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id_book"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("year_publication"),
                    rs.getString("image"),
                    rs.getString("description"),
                    rs.getString("position")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
        return books;
    }

    /**
     * Met à jour les informations d'un livre
     * @param book Le livre à modifier
     * @return true si la mise à jour est réussie, false sinon
     */
    @Override
    public boolean update(Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, isbn = ?, position = ?, year_publication = ?, " +
                     "image = ?, description = ? WHERE id_book = ? AND status = 'Active'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getIsbn());
            stmt.setString(4, book.getPosition());
            stmt.setInt(5, book.getYearPublication());
            stmt.setString(6, book.getImage());
            stmt.setString(7, book.getDescription());
            stmt.setInt(8, book.getIdBook());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du livre : " + e.getMessage());
        }
        return false;
    }

    /**
     * Suppression logique d'un livre et de ses exemplaires
     * @param book Le livre à supprimer
     * @return true si la suppression est réussie, false sinon
     */
    @Override
    public boolean delete(Book book) {
        try {
            connection.setAutoCommit(false);

            // Suppression logique des exemplaires liés
            String deleteCopiesSQL = "UPDATE copy SET status = 'Inactive' WHERE book_id = ?";
            try (PreparedStatement stmtCopies = connection.prepareStatement(deleteCopiesSQL)) {
                stmtCopies.setInt(1, book.getIdBook());
                stmtCopies.executeUpdate();
            }

            // Suppression logique du livre
            String deleteBookSQL = "UPDATE book SET status = 'Inactive' WHERE id_book = ?";
            try (PreparedStatement stmtBook = connection.prepareStatement(deleteBookSQL)) {
                stmtBook.setInt(1, book.getIdBook());
                int affectedRows = stmtBook.executeUpdate();
                if (affectedRows > 0) {
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Erreur lors du rollback : " + rollbackEx.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.err.println("Erreur lors du rétablissement de l'autocommit : " + ex.getMessage());
            }
        }
        return false;
    }
}
