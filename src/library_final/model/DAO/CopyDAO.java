/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.DAO;

import library_final.model.entity.Copy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import library_final.config.DatabaseConnection;

/**
 *
 * @author fredi
 */

/**
 * DAO pour la gestion des exemplaires de livres
 * Implémente les opérations CRUD spécifiques aux exemplaires
 */
public class CopyDAO implements DAO<Copy> {

    private Connection connection;

    public CopyDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Ajoute un nouvel exemplaire en base de données
     * @param copy L'exemplaire à ajouter
     * @return true si l'ajout est réussi, false sinon
     */
    @Override
    public boolean create(Copy copy) {
        String sql = "INSERT INTO copy (book_id, code) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, copy.getIdBook());
            stmt.setString(2, copy.getCode());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'exemplaire : " + e.getMessage());
            return false;
        }
    }

    /**
     * Recherche un exemplaire par son ID
     * @param id Identifiant unique de l'exemplaire
     * @return L'exemplaire trouvé ou null si inexistant
     */
    @Override
    public Copy findById(int id) {
        String sql = "SELECT * FROM copy WHERE id_copy = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Copy(
                    rs.getInt("id_copy"),
                    rs.getInt("book_id"),
                    rs.getString("code"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'exemplaire : " + e.getMessage());
        }
        return null;
    }

    /**
     * Retourne la liste de tous les exemplaires enregistrés
     * @return Liste des exemplaires
     */
    @Override
    public List<Copy> findAll() {
        List<Copy> copies = new ArrayList<>();
        String sql = "SELECT * FROM copy";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                copies.add(new Copy(
                    rs.getInt("id_copy"),
                    rs.getInt("book_id"),
                    rs.getString("code"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des exemplaires : " + e.getMessage());
        }

        return copies;
    }

    /**
     * Met à jour un exemplaire existant
     * @param copy L'exemplaire avec ses nouvelles informations
     * @return true si la mise à jour réussit, false sinon
     */
    @Override
    public boolean update(Copy copy) {
        String sql = "UPDATE copy SET code = ?, status = ? WHERE id_copy = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, copy.getCode());
            stmt.setString(2, copy.getStatus());
            stmt.setInt(3, copy.getIdCopy());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'exemplaire : " + e.getMessage());
            return false;
        }
    }

    /**
     * Suppression logique d'un exemplaire (passage en 'Inactive')
     * @param copy L'exemplaire à désactiver
     * @return true si la suppression réussit, false sinon
     */
    @Override
    public boolean delete(Copy copy) {
        String sql = "UPDATE copy SET status = 'Inactive' WHERE id_copy = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, copy.getIdCopy());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'exemplaire : " + e.getMessage());
            return false;
        }
    }
}
