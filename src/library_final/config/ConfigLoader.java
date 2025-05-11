/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fredi
 */
public class ConfigLoader {
    
    private static Properties properties = new Properties();
    
    static{
        try(FileInputStream input = new FileInputStream("src/library_final/config/config.properties")){
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur: Impossible de charger le fichier de configuration");
        }
    }
    
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
    
}
