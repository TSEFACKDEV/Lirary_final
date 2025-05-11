/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.entity;

/**
 *
 * @author fredi
 */
public class Copy {
    
    private int idBook;
    private int idCopy;
    private String code;
    private String status;
    
    public Copy(int idCopy, int idBook, String code, String status)
    {
        this.idCopy = idCopy;
        this.code = code;
        this.idBook = idBook;
        this.status = status;
    }
    public Copy(int idCopy, int idBook, String code)
    {
        this.idCopy = idCopy;
        this.code = code;
        this.idBook = idBook;
    }
    
    public Copy(int idBook, String code)
    {
        this.code = code;
        this.idBook = idBook;
    }
    
    public int getIdCopy()
    {
        return idCopy;
    }
    public void setIdCopy(int idCopy)
    {
        this.idCopy = idCopy;
    }
    
        public int getIdBook()
    {
        return idBook;
    }
    
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
