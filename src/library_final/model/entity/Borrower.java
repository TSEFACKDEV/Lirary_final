/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fredi
 */
public class Borrower extends Person{
    
    private static final int MAX_LOAN = 3;
    private final List<Loan> loans;

    public Borrower(int id, String name, String nic, String phone, String email) {
        super(id, name, nic, phone, email);
        this.loans = new ArrayList<>();
    }
    
    public List<Loan> getLoans()
    {
        return new ArrayList<>(loans) ;
    }
    
    public int getMaxLoan() {
        return MAX_LOAN; 
    }
     
    public boolean addLoan(Loan loan) {
        if (loan != null && loans.size() < MAX_LOAN) {
            return loans.add(loan);
        }
        return false; // Rejeté si trop de prêts
    }
    
    public boolean removeLoan(Loan loan) {
        return loans.remove(loan);
    }
}
