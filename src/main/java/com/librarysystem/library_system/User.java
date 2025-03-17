package com.librarysystem.library_system;

import jakarta.persistence.Entity; //Spring boot will treat it as a table now. Each instance will be a new row.
import jakarta.persistence.Id;//Creates a primary key for the table
import jakarta.persistence.GeneratedValue; //This goes with the primary key. Everytime a new instace is added,it automatically will add a primary key value
import jakarta.persistence.GenerationType;//Will be used for what the type of the primaray key will be 

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String username;
    private String password;
    private String role;

    private User(){} // I made this a private constructor because I want users to must create a username and password. I still have a default construstor as it is required for JPA
    
    public User(String username, String passwor, String role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId()
    {
        return id;
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

}
