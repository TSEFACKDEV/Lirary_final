/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.entity;

/**
 *La classe User généralise le libraire et le bibliothécaire
 * @author fredi
 */
public abstract class User extends Person{
    
    private String password;
    private String status;
    private String resetCode;

    /**
     * 
     * @param id
     * @param name
     * @param nic
     * @param phone
     * @param email
     * @param password
     * @param status 
     */
    public User(int id, String name, String nic, String phone, String email, String password, String status)
    {
        super(id, name, nic, phone, email);
        this.password = password;
        this.status = status;
    }
    
    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getResetCode()
    {
        return resetCode;
    }
    public void setResetCode(String resetCode)
    {
        this.resetCode = resetCode;
    }
    
}
