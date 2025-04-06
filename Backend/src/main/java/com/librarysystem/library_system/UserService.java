// This will handle the autentication of users and compare their inputted password to their actaul password
// The inout recieved by the controller will be sent here to check if the user will be auntheticated or no

package com.librarysystem.library_system;

import org.springframework.beans.factory.annotation.Autowired; // Used to inject the UserRepository dependency so that it can be used to interact with the database in the controller
import org.springframework.stereotype.Service;  // Import the @Service annotation to mark this class as a Spring service - whihc means this code contains logic
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String authenticateUser(String username, String password)
    {
        Optional<User> checkuser = userRepository.findByUsername(username);
        if(!checkuser.isPresent())
        {
            return "Incorrect";
        }

        User user = checkuser.get();

        if(password.equals(user.getPassword()))
        {
            return user.getRole();
        }
        else{
            return "Incorrect";
        }
        
    }
}
