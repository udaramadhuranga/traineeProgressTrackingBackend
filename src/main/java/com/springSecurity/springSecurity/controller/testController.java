package com.springSecurity.springSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class testController {

    @GetMapping("/home")
    public String userAccessHome() {
        return "This is home page";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('TRAINER') or hasRole('ADMIN')")
    public String userAccessDshboard() {
        return "This is dashboard";
    }

    @GetMapping("/trainee")
    @PreAuthorize("hasRole('ADMIN')")
    public String userAccessTrainee() {
        return "This is trainee";
    }

    @GetMapping("/manage")
    @PreAuthorize("hasRole('ADMIN')")
    public String userAccessManage() {
        return "This is dashboard";
    }


}
