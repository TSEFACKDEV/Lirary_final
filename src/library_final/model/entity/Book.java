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
public class Book {
    
    private int id_book;
    private String title;
    private String author;
    private String isbn;
    private String position;
    private int year_publication;
    private String image;
    private String description;
    private String status;
    private List<Copy> copies;

    public Book(int id_book, String title, String author, String isbn, int year_publication, String image, String description) {
        this.id_book = id_book;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.year_publication = year_publication;
        this.image = image;
        this.description = description;
        this.position = position;
        this.copies = new ArrayList<>();
    }
    
    public int getIdBook()
    {
        return id_book;
    }
    public void setIdBook(int idBook)
    {
        this.id_book = idBook;
    }
     
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title =title;
    }
    
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author =author;
    }
    
    public String getIsbn()
    {
        return isbn;
    }
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }
    
    public String getPosition()
    {
        return position;
    }
    public void setPosition(String position)
    {
        this.position = position;
    }
    
    public int getYearPublication()
    {
        return year_publication;
    }
    public void setYearPublication(int year_publication)
    {
        this.year_publication = year_publication;
    }
    
    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image = image;
    }
    
   public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Copy> getCopies()
    {
        return Collections.unmodifiableList(copies);
    }
    
    public void addCopy(Copy copy)
    {
        if(copy !=  null && !copies.contains(copy))
        {
            copies.add(copy);
        }
    }
    
    
}
