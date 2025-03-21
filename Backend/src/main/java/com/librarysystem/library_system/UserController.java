package com.librarysystem.library_system;

import org.springframework.beans.factory.annotation.Autowired;
import com.librarysystem.library_system.UserService; // Imports UserService class from my project
import com.librarysystem.library_system.LoginRequest; // Import LoginRequest class from my project
import org.springframework.web.bind.annotation.*; //used to handle HTTP requests in Spring



@RestController // Marks this as a controller handling HTTP requests.
@RequestMapping("/users") // the base path for the URL of all the operations related to “books.”
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String role = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if(role.equals("admin"))
        {
            return "Welcome, admin!";
        }
        else{
            return "Welcome, student!";
        }
    }
    
}
