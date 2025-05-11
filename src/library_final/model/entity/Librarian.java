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
public class Librarian extends User{
    
    private final List<Book> books;
    private List<Borrower> borrowers;
    
    
    public Librarian(int id, String name, String nic, String phone, String email, String password, String status)
    {
        super(id, name, nic, phone, email, password, status);
        this.books = new ArrayList<>();
    }
    
    public List<Book> getBooks()
    {
        return new ArrayList<>(books);
    }
    
    public void addBook(Book book)
    {
        if(book != null && !books.contains(book))
        {
            books.add(book);
        }
    }
    
    public List<Borrower> getBorrowers()
    {
        return new ArrayList<>(borrowers);
    }

    public void addBorrower(Borrower borrower)
    {
        if(borrower != null && !borrowers.contains(borrower))
        {
            borrowers.add(borrower);
        }
    }
    
}
