/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.DAO;

import library_final.model.entity.Loan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library_final.config.DatabaseConnection;

/**
 * Implémente les opérations de CRUD spécifiques aux emprunts
 * @author fredi
 */
public class LoanDAO implements DAO<Loan>{
            
    private final Connection connection;

    public LoanDAO(){
        this.connection = DatabaseConnection.getConnection();
    }

    /**
     * Ajoute un nouvel emprunt en base de données
     * @param loan L'emprunt à enregistrer
     * @return true si l'ajout est réussi, false sinon
     */
    @Override
    public boolean create(Loan loan){
        String sqlCreate = "INSERT INTO loan(borrower_id, copy_id, librarian_id, date_loan, exp_return_date, act_return_date) VALUES(?, ?, ?, ?, ?, NULL)";
        try(PreparedStatement stmt = connection.prepareStatement(sqlCreate);){
            stmt.setInt(1, loan.getidBorrower());
            stmt.setInt(2, loan.getIdCopy());
            stmt.setInt(3, loan.getIdLibrarian());
            stmt.setDate(4, loan.getDateLoan());
            stmt.setDate(5, loan.getExpReturnDate());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'emprunt :"+e.getMessage());
            return false;
        }
    }
    
    @Override
    public Loan findById(int idLoan){
        
        String sql = "SELECT * FROM loan WHERE id_loan = ?";
        try(PreparedStatement stmt  = connection.prepareStatement(sql)){
            stmt.setInt(1, idLoan);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return new Loan(
                    rs.getInt("id_loan"),
                    rs.getInt("copy_id"),
                    rs.getInt("borrower_id"),
                    rs.getInt("librarian_id"),
                    rs.getDate("date_loan"),
                    rs.getDate("exp_return_date"),
                    rs.getDate("act_return_date")
                );
            }
            
        }catch(SQLException e){
            System.err.println("Erreur de recherche de l'emprunt: "+e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Loan> findAll(){
        
        List<Loan> loans = new ArrayList();
        
        String sql = "SELECT * FROM loan";
        try(PreparedStatement stmt  = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                loans.add(new Loan(
                    rs.getInt("id_loan"),
                    rs.getInt("copy_id"),
                    rs.getInt("borrower_id"),
                    rs.getInt("librarian_id"),
                    rs.getDate("date_loan"),
                    rs.getDate("exp_return_date"),
                    rs.getDate("act_return_date")
                ));
            }
            
        }catch(SQLException e){
            System.err.println("Erreur de la récupération des emprunts: "+e.getMessage());
        }
        return loans;
    }
    
    /**
     * Met à jour un emprunt existant (Enregistrement du retour)
     * @param loan L'emprunt
     * @return true si la mise à jour est réussie, false sinon
     */
    @Override
    public boolean update(Loan loan){
        String sql = "UPDATE loan SET act_return_date = ? WHERE id_loan = ?";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDate(1, loan.getActReturnDate());
            stmt.setInt(2, loan.getIdLoan());
            
            return stmt.executeUpdate() > 0;
            
        }catch(SQLException e){
            System.err.println("Erreur lors de la mise à jour "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Loan loan) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /**
     * Nombre total d'emprunts enregistrés
     * @return Nombre total d'emprunts
     */
    public int getTotalLoans(){
        String sql = "SELECT COUNT(*) FROM Loan";
        try(Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du comptage des emprunts: "+e.getMessage());
        }
        return 0;
    }
    
    /**
     * Top 5 des livres les plus empruntés
     * @return Liste des titres des livres les plus empruntés
     */
    public List<String> getTop5BorrowedBooks() {
        List<String> books = new ArrayList<>();
        String sql = "SELECT b.title, COUNT(l.id_loan) as total_loans " +
                     "FROM Loan l JOIN Copy c ON l.copy_id = c.id_copy " +
                     "JOIN Book b ON c.book_id = b.id_book " +
                     "GROUP BY b.title ORDER BY total_loans DESC LIMIT 5";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                books.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul des livres les plus empruntés : " + e.getMessage());
        }
        return books;
    }

    /**
     * Taux de retour à temps
     * @return Pourcentage de retour à temps
     */
    public double getOnTimeReturnRate() {
        String sql = "SELECT (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Loan WHERE act_return_date IS NOT NULL)) " +
                     "AS rate FROM Loan WHERE act_return_date <= exp_return_date";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("rate");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul du taux de retour à temps : " + e.getMessage());
        }
        return 0.0;
    }
    

    /**
     * Taux d'utilisation des exemplaires (%) = (Nombre d'exemplaires empruntés / Nombre total d'exemplaires) * 100
     * @return Pourcentage d'utilisation
     */
    public double getCopyUsageRate() {
        String sql = "SELECT (COUNT(DISTINCT copy_id) * 100.0 / (SELECT COUNT(*) FROM Copy)) AS usage_rate FROM Loan";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("usage_rate");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul du taux d'utilisation des exemplaires : " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Utilisateur ayant emprunté le plus de livres
     * @return Nom de l'utilisateur et nombre d'emprunts
     */
    public String getTopBorrower() {
        String sql = "SELECT p.name, COUNT(l.id_loan) AS total_loans " +
                     "FROM Loan l JOIN Borrower b ON l.borrower_id = b.id " +
                     "JOIN Person p ON b.id = p.id " +
                     "GROUP BY p.name ORDER BY total_loans DESC LIMIT 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString("name") + " avec " + rs.getInt("total_loans") + " emprunts";
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du meilleur emprunteur : " + e.getMessage());
        }
        return "Aucun emprunt trouvé";
    }

}