package com.librarysystem.library_system;

import jakarta.persistence.Entity; //Spring boot will treat it as a table now. Each instance will be a new row.
import jakarta.persistence.Id;//Creates a primary key for the table
import jakarta.persistence.GeneratedValue; //This goes with the primary key. Everytime a new instace is added,it automatically will add a primary key value
import jakarta.persistence.GenerationType;//Will be used for what the type of the primaray key will be 
import java.util.Date;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String author;
    private boolean isAvailable;
    private Date borrowedDate;
    private String borrowedBy;

    public Book() {}
    
    public Book(String title, String author)
    {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowedDate = null;
        this.borrowedBy = null;
    }

    public Book(String title, String author, boolean isAvailable, String oBy, Date borrowedDate)
    {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.borrowedBy = borrowedBy;
        this.borrowedDate = borrowedDate;
    }

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public boolean isAvailable()
    {
        return isAvailable;
    }

    public Date getBorrowedDate()
    {
        return borrowedDate;
    }

    public String getBorrowedBy()
    {
        return borrowedBy;
    }

    public void setIsAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }

    public void setBorrowedDate(Date borrowedDate)
    {
        this.borrowedDate = borrowedDate;
    }

    public void setBorrowedBy(String student)
    {
        this.borrowedBy = student;
    }



}
