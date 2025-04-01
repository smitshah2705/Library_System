package com.librarysystem.library_system;

import jakarta.persistence.Entity; // User class will be treated as a table
import jakarta.persistence.Id;//Creates a primary key for the table
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue; //This goes with the primary key. Everytime a new instace is added,it automatically will add a primary key value
import jakarta.persistence.GenerationType;//Will be used for what the type of the primaray key will be 
import java.util.Date;
import java.util.Calendar;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String author;
    private boolean isAvailable;
    private Date borrowedDate;
    private Date dueDate;
    private boolean overdue;

    @ManyToOne // This represents the relationship between Book enitity and User entity. It means many of book objects can be associated with one User
    @JoinColumn(name = "user_id")
    private User borrowedBy; 

    protected Book() {} //Required for Spring
    
    public Book(String title, String author)
    {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.borrowedDate = null;
        this.dueDate = null;
        this.borrowedBy = null;
        this.overdue = false;
    }

    public Book(String title, String author, boolean isAvailable, Date borrowedDate, User borrowedBy)
    {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
        this.borrowedDate = borrowedDate;
        this.borrowedBy = borrowedBy;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowedDate);
        calendar.add(calendar.DATE, 30);
        this.dueDate = calendar.getTime();

        this.overdue = false;

    }

    public int getId()
    {
        return id;
    }

    //For Tets
    public void setId(Integer ID)
    {
        id = ID;
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

    public User getBorrowedBy()
    {
        return borrowedBy;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public boolean getOverDue()
    {
        return overdue;
    }

    public void setIsAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }

    public void setBorrowedDate(Date borrowedDate)
    {
        this.borrowedDate = borrowedDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(borrowedDate);
        calendar.add(calendar.DATE, 30);
        dueDate = calendar.getTime();
    }

    public void setBorrowedBy(User user)
    {
        borrowedBy = user;
    }

    public void setOverDue(Boolean overdue)
    {
        this.overdue = overdue;
    }



}
