package com.openclassrooms.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {


    // l’annotation @GetMapping pour associer l’URL.
    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome, Admin";
    }

    @GetMapping("/")
    public String getGitHub(Principal user) {
        return "Welcome, GitHub User: " + user.getName() + ", userToString: { " + user +" }";
    }


}
