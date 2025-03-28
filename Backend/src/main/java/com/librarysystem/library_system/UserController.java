package com.librarysystem.library_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; 



@RestController 
@RequestMapping("/users") 
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login/{username}/{password}")
    public String login(@PathVariable("username") String username, @PathVariable("password") String password ) { 
        String message = userService.authenticateUser(username, password);
        if(message.equals("Admin"))
        {
            return "Welcome, admin!";
        }
        if(message.equals("Student")){
            return "Welcome, student!";
        }
        return "Your username or password is incorrect";
        
    }
    
}
