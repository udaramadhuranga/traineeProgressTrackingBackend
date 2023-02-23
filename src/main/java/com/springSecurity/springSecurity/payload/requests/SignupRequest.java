package com.springSecurity.springSecurity.payload.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String username;


    private String email;

    private Set<String> roles;


    private String password;


    private String address;


    private String phoneNo;
}
