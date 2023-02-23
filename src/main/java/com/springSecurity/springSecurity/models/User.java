package com.springSecurity.springSecurity.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 300)
    private String address;

    @NotBlank
    @Size(max = 300)
    private String phoneNo;



    @DBRef
    private Set<Role> roles = new HashSet<>();


    public User() {
    }

    public User(String username, String email, String password, String address, String phoneNo, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNo = phoneNo;
        this.roles = roles;
    }

    public User(String username, String email, String password, String address, String phoneNo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
