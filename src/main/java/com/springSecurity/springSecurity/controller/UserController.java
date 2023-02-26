package com.springSecurity.springSecurity.controller;

import com.springSecurity.springSecurity.Repository.RoleRepository;
import com.springSecurity.springSecurity.Repository.UserRepositiory;
import com.springSecurity.springSecurity.models.ERole;
import com.springSecurity.springSecurity.models.Role;
import com.springSecurity.springSecurity.models.User;

import com.springSecurity.springSecurity.payload.requests.SignupRequest;

import com.springSecurity.springSecurity.services.UserService;
import com.springSecurity.springSecurity.services.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.ResponseEntity;


import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.Set;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {



    @Autowired
    UserRepositiory userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);


//User adding function for requirement admin can add users.
    @PostMapping("/by-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getAddress(),signUpRequest.getPhoneNo());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles.size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: User Role is null");
        } else {
            strRoles.forEach(role -> {
                switch (role) {


                    case "trainer":
                        Role trainerRole = roleRepository.findByName(ERole.ROLE_TRAINER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(trainerRole);

                        break;

                    case "trainee":
                        Role traineeRole = roleRepository.findByName(ERole.ROLE_TRAINEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(traineeRole);

                        break;
                    default:
                        Role userRole = null;

                }
            });
        }

        if(roles.size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Not allowed to perform this task or incorrect user inputs");

        }else {
            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");

        }
    }

    //User adding function for requirement trainer can add trainees.
    @PostMapping("/by-trainer")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> registerTrainee( @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),signUpRequest.getAddress(),signUpRequest.getPassword());

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles.size() == 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: User Role is null");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "trainee":
                        Role traineeRole = roleRepository.findByName(ERole.ROLE_TRAINEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(traineeRole);

                        break;
                    default:
                        Role userRole = null;
                }
            });
        }

        if(roles.size() == 0) {

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok("User registered successfully!");

        }else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Not allowed to perform this task or incorrect user inputs");
        }
    }



    //admin All users delete function
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {

            return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //trainer delete trainee only allowing function
    @DeleteMapping("/by-trainer/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<String> deleteTrainee(@PathVariable String id) {
        try {

            String result = userService.deletetraineeById(id);

            if (result.equals("valid")){
                return new ResponseEntity<>("user deleted success", HttpStatus.OK);
            }else{
                return ResponseEntity
                        .badRequest()
                        .body("Error: Not Authorized or User not found");
            }

        } catch (Exception e) {

            return new ResponseEntity<>(""+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //admin update user controller
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <User> updateUserByAdmin(@RequestBody SignupRequest user, @PathVariable String id){

        User updateUser = userService.updateUser(user, id);
        if(updateUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        }

    }

    //trainer trainee only allowed controller
    @PutMapping("/by-trainer/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity <User> updateUserByTrainer(@RequestBody SignupRequest user, @PathVariable String id){

        User updateUser = userService.updateUser(user, id);
        if(updateUser == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(userService.updateTrainee(user,id), HttpStatus.OK);
        }

    }

    @GetMapping("/trainers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllTrainers() {
        try {

            logger.info("get all trainer before calling services");

            return new ResponseEntity<>(userService.getAllTrainers(), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/trainees")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<List<User>> getAllTrainees() {
        try {

            logger.info("get all trainer before calling services");

            return new ResponseEntity<>(userService.getAllTrainees(), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
