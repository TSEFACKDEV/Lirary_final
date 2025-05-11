
import java.util.List;
import library_final.model.DAO.CopyDAO;
import library_final.model.entity.Copy;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author fredi
 */
public class CopyDAOTest {
    
    public static void main(String[] args){
        
        CopyDAO copyDAO = new CopyDAO();
        
        Copy copy = new Copy(9, "23252GJ");
        
        copyDAO.create(copy);
        
        Copy copyFinded = copyDAO.findById(1);
       
        System.out.println("L'exemplaire : " + copyFinded.getCode() + " | Statut : " + copyFinded.getStatus());
        
        List<Copy> copies = copyDAO.findAll();
        
        for(Copy c : copies){
            System.out.println("L'exemplaire : " + c.getCode() + " | Statut : " + c.getStatus()+"\n----------------------------\n");
        }
        
        copyFinded.setStatus("Loaned");
        copyDAO.update(copyFinded);
        
        copyDAO.delete(copyFinded);
        
        
        
    }
    
}
