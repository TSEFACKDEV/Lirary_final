/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author fredi
 */
public class Administrator extends User{
    
    private final List<Librarian> librarians;
    
   public Administrator(int id, String name, String nic, String phone, String email, String password, String status) {
        super(id, name, nic, phone, email, password, status);
        this.librarians = new ArrayList<>();
    }
    
   /**
    * Renvoie la liste des bibliothécaires
    * @return 
    */
    public List<Librarian> getLibrarians() 
    {
        return new ArrayList<>(librarians);
    }

    /**
     * Ajoute un bibliothécaire à la liste s'il n'est pas déjà présent.
     *
     * @param librarian Le bibliothécaire à ajouter
     */
    public void addLibrarian(Librarian librarian) {
        if (librarian != null && !librarians.contains(librarian)) 
        {
            librarians.add(librarian);
        }
    }
}

