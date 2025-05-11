/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.DAO;

import library_final.model.entity.User;
import java.sql.*;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static library_final.config.DatabaseConnection.connection;
import library_final.model.entity.Borrower;

/**
 *
 * @author fredi
 */
public class BorrowerDAO implements DAO<Borrower>{
    
    private final Connection connection;
    
    public BorrowerDAO(Connection connection){
        this.connection = connection;
    }
    
    @Override
    public boolean create (Borrower borrower){
        if(borrower == null){
            throw new IllegalArgumentException("Les données de l'emprunteur sont invalides");
        }
        try {
            
            connection.setAutoCommit(false);
            
            String sqlPerson = "INSERT INTO person(name, nic, phone, email) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtPerson = connection.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
            stmtPerson.setString(1, borrower.getName());
            stmtPerson.setString(2, borrower.getNic());
            stmtPerson.setString(3, borrower.getPhone());
            stmtPerson.setString(4, borrower.getEmail());
            stmtPerson.executeUpdate();
            
            ResultSet rs = stmtPerson.getGeneratedKeys();
            if(rs.next()){
                borrower.setId(rs.getInt(1));
            }
            
            String sqlUser = "INSERT INTO borrower(id, max_loan) VALUES (?, ?)";
            PreparedStatement stmtUser = connection.prepareStatement(sqlUser);
            stmtUser.setInt(1, borrower.getId());
            stmtUser.setInt(2, borrower.getMaxLoan());
            stmtUser.executeUpdate();
            
            connection.commit();
            
            return true;
            
        } catch (SQLException e) {
            try{
                connection.rollback();
            }catch(SQLException rollbackE){
                rollbackE.printStackTrace();
            } 
            e.printStackTrace();
        }finally{
            try{
                connection.setAutoCommit(true);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    
    @Override
    public Borrower findById(int id){
        try{
            String sql = "SELECT * FROM person p JOIN borrower b ON p.id = b.id WHERE p.id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Borrower(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nic"),
                        rs.getString("phone"),
                        rs.getString("email")
                );
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Borrower> findAll(){
        List<Borrower> borrowers = new ArrayList<>();
        try{
            String sql = "SELECT * FROM person p JOIN borrower b ON p.id=b.id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                borrowers.add(new Borrower(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nic"),
                        rs.getString("phone"),
                        rs.getString("email")
                ));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return borrowers;
    }
    
    @Override
    public boolean update(Borrower borrower){
        try{
            String sqlPerson = "UPDATE person SET name = ?, nic = ?, phone = ?, email = ? WHERE id = ?";
            PreparedStatement stmtPerson = connection.prepareStatement(sqlPerson);
            stmtPerson.setString(1, borrower.getName());
            stmtPerson.setString(2, borrower.getNic());
            stmtPerson.setString(3, borrower.getPhone());
            stmtPerson.setString(4, borrower.getEmail());
            stmtPerson.setInt(5, borrower.getId());
            stmtPerson.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        return false;
        }
    }
    
    @Override
    public boolean delete(Borrower borrower){
        try{
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
