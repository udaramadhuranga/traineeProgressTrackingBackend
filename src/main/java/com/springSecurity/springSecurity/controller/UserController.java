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

     /**
      *Controller for adding Users. Only Admins allow to use the route
      * Can add Any user type users
      *Post Request
      *@param signUpRequest Request body object
      *@return User with the status code
     */
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
            User savedUser =userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        }
    }

    /**
     * Controller for only adding trainees. Satisfy requirement trainers can add trainees
     * Post Request
     * @param signUpRequest Request body object
     * @return User with the status code
     */
    @PostMapping("/by-trainer")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
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
        if(roles.size() != 0) {
            user.setRoles(roles);
            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);
        }else {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Not allowed to perform this task or incorrect user inputs");
        }
    }

    /**
     * Delete controller. Allows to delete all users.
     * Access allows only to admins
     * Delete Request
     * @param id deleting userid
     * @return String (id of deleted user)
     * Catch all exceptions
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete controller. Allows to delete only trainees.
     * Access allows only to admins and trainers
     * Delete Request
     * @param id deleting userid
     * @return String (id of deleted user)
     * Catch all exceptions
     */
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

    /**
     * Update Controller . Allows to update any user
     * Access allows only to admins
     * Put Request
     * @param user Request body object
     * @param id updating userid
     * @return User
     * Catch all exceptions
     */
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

    /**
     *  Update Controller . Allows to update only trainees
     *  Access allows only to admins and trainers
     *  Satisfy requirement trainers can update trainees
     *  Put Request
     * @param user Request body object
     * @param id updating userid
     * @return User
     * Catch all exceptions
     */
    @PutMapping("/by-trainer/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity <User> updateUserByTrainer(@RequestBody SignupRequest user, @PathVariable String id){
        try {
            logger.info("update trainee by trainee before calling services");
            User updateUser = userService.updateUser(user, id);
            if (updateUser == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(userService.updateTrainee(user, id), HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Controller
     * Get all trainers filtering trough db according to the role
     * Access allows to only admins
     * Get Request
     * @return List<User>
     * Catch all exceptions
     */
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

    /**
     *Get Controller
     * Get all trainees filtering trough db according to the role
     * Access allows to only admins and trainers
     * @return List<User>
     * Catch all exceptions
     */
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
