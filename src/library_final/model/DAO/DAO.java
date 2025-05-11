/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package library_final.model.DAO;

import java.util.List;

/**
 * Interface générique pour les opérations CRUD
 * @author fredi
 * @param <T> Type de l'entité (Person, Book, etc.)
 */
public interface DAO<T> {
    
    /**
     * Ajouter un nouvel objet dans la base de données
     * @param obj 
     * @return true si l'ajout est réussi, false sinon
     */
    boolean create(T obj);
    
    /**
     * Récupère un objet par son id
     * @param id
     * @return L'oblet correspondant, null s'il est introuvable
     */
    T findById(int id);
    
    /**
     * Recupère tous les objets de la table
     * @return Une liste d'objets
     */
    List<T> findAll();
    
    /**
     * Met à jour un objet existant
     * @param obj L'objet à mettre à jour
     * @return true si la mise à jour est réussie, false sinon
     */
    boolean update(T obj);
    
    /**
     * Suppression logique d'un objet de la base de données
     * @param obj
     * @return true si la suppression est réussie, false sinon
     */
    boolean delete(T obj);
}
