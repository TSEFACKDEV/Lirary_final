/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author fredi
 */
public class DatabaseConnection {
    
    public static Connection connection = null;
    
    public static Connection getConnection(){
        
        if(connection == null){
            try{
                Class.forName(DatabaseConfig.DRIVER);
                connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
                System.out.println("Connection réussie");
            }catch(ClassNotFoundException|SQLException e){
                System.err.println("Erreur: "+e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection(){
        if(connection != null){
            try{
                connection.close();
                connection = null;
                System.out.println("Connection fermée");
            }catch(SQLException e){
                System.err.println("Erreur de fermeture: "+e.getMessage());
            }
        }
    }
    
}
