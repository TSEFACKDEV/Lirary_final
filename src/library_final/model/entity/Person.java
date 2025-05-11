/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.entity;

/**
 * La classe Person est la classe générale qui représente tous les individus présents dans le système
 * @author fredi
 */
public abstract class Person {
    
    protected int id;
    protected String name;
    protected String nic;
    protected String phone;
    protected String email;
    
    /**
     * Constructeur de la classe Person
     * @param id {int}
     * @param name {String}
     * @param nic {String}
     * @param phone {String}
     * @param email {String}
     */
    public Person(int id, String name, String nic, String phone, String email)
    {
        this.id = id;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.email = email;
    }
    
    
    //Getters et setters
    
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getNic()
    {
        return nic;
    }
    public void setNic(String nic)
    {
        this.nic = nic;
    }
    
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
}
