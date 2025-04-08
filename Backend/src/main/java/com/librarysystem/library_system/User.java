package com.librarysystem.library_system;

import jakarta.persistence.Entity; 
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType;
import java.util.List;
import jakarta.persistence.Transient;
import java.util.ArrayList;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String username;
    private String password;
    private String role;

    @OneToMany(mappedBy = "borrowedBy") //One to many relationship is used because oneuser can borrow multiple books.
    private List<Book> borrowedBooks = new ArrayList<Book>();
    
    @Transient
    private List<Book> overDueBooks = new ArrayList<Book>();

    private User(){} 

    public User(String username, String password, String role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer ID)
    {
        id = ID;
    }
    
    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role; 
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public List<Book> getOverDueBooks()
    {
        return overDueBooks;
    }


}
