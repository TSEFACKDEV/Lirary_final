/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library_final.model.entity;

import java.sql.Date;

/**
 *
 * @author fredi
 */
public class Loan {
    
    private int idLoan;             // Identifiant unique du prêt
    private Date dateLoan;          // Date de l'emprunt
    private Date expReturnDate;     // Date de retour prévue
    private Date actReturnDate;     // Date de retour réelle (null si pas encore retourné)
    private int idBorrower;      // Emprunteur
    private int idCopy;          // Exemplaire emprunté
    private int idLibrarian;

    /**
     * Constructeur de la classe Loan.
     *
     * @param idLoan Identifiant du prêt
     * @param dateLoan Date de l'emprunt
     * @param expReturnDate Date de retour prévue
     * @param actReturnDate Date de retour effective (peut être null)
     * @param borrower Emprunteur du livre
     * @param loanCopy Exemplaire emprunté
     */
    public Loan(int idLoan,int idCopy, int idBorrower, int idLibrarian, Date dateLoan, Date expReturnDate, Date actReturnDate) {
        this.idLoan = idLoan;
        this.dateLoan = dateLoan;
        this.expReturnDate = expReturnDate;
        this.actReturnDate = actReturnDate;
        this.idBorrower = idBorrower;
        this.idCopy = idCopy;
        this.idLibrarian = idLibrarian;
    }

    // Getter et setter pour idLoan
    public int getIdLoan() 
    {
        return idLoan;
    }

    public void setIdLoan(int idLoan) 
    {
        this.idLoan = idLoan;
    }

    // Getter et setter pour dateLoan
    public Date getDateLoan() 
    {
        return dateLoan;
    }

    public void setDateLoan(Date dateLoan) 
    {
        this.dateLoan = dateLoan;
    }

    // Getter et setter pour expReturnDate
    public Date getExpReturnDate() 
    {
        return expReturnDate;
    }

    public void setExpReturnDate(Date expReturnDate) 
    {
        this.expReturnDate = expReturnDate;
    }

    // Getter et setter pour actReturnDate
    public Date getActReturnDate() 
    {
        return actReturnDate;
    }

    public void setActReturnDate(Date actReturnDate) 
    {
        this.actReturnDate = actReturnDate;
    }

    // Getter et setter pour borrower
    public int getidBorrower() 
    {
        return idBorrower;
    }

    public void setBorrower(int idBorrower) 
    {
        this.idBorrower = idBorrower;
    }

    // Getter et setter pour loanCopy
    public int getIdCopy() 
    {
        return idCopy;
    }

    public void setIdCopy(int idCopy) 
    {
        this.idCopy = idCopy;
    }
    
     public int getIdLibrarian() 
    {
        return idLibrarian;
    }

    public void setIdLibrarian(int idLibrarian) 
    {
        this.idLibrarian = idLibrarian;
    }

}