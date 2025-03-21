//user sends their username and password as a JSON object in the login request, the LoginRequest class will be used to map that JSON data into an actual Java object with username and password fields

package com.librarysystem.library_system;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest()
    {}

    public LoginRequest(String username,String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
