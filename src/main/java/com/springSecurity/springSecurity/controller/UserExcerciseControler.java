package com.springSecurity.springSecurity.controller;

import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.models.User;
import com.springSecurity.springSecurity.models.UserExcercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;
import com.springSecurity.springSecurity.payload.requests.SignupRequest;
import com.springSecurity.springSecurity.payload.requests.UserExcerciseRequest;
import com.springSecurity.springSecurity.services.UserExerciseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user-exercise")
public class UserExcerciseControler {

    @Autowired
    UserExerciseService exerciseService;
    Logger logger = LoggerFactory.getLogger(UserExcerciseControler.class);
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<UserExcercise> addUserCourse(@RequestBody UserExcerciseRequest userExcerciseRequest) {

        try{


            return new ResponseEntity<>(exerciseService.addUserCourse(userExcerciseRequest), HttpStatus.CREATED);

        }catch (Exception e){



            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/trainee-all-courses/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('TRAINEE') ")
    public ResponseEntity <List<UserExcercise>> getAllCoursesOfUser(@PathVariable String id) {

        try{
            logger.info("User-Exercise controller before calling trainee-all-courses  get ");
            return new ResponseEntity<>(exerciseService.getTraineeExercises(id), HttpStatus.OK);

        }catch (Exception e){

                logger.info(e.getMessage());

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity <UserExcercise> updateUserExercise(@RequestBody UserExcerciseRequest userExcerciseRequest, @PathVariable String id){

        try {
            UserExcercise userExcercise = exerciseService.updateUserExercise(userExcerciseRequest, id);
            if (userExcercise == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(userExcercise, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/state/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('TRAINEE') ")
    public ResponseEntity <UserExcercise> updateUserExerciseState(@RequestBody UserExcerciseRequest userExcerciseRequest, @PathVariable String id){

        try {
            UserExcercise userExcercise = exerciseService.updateUserExerciseState(userExcerciseRequest, id);
            if (userExcercise == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(userExcercise, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity <String> deleteUserExercise(@PathVariable String id){

        try {
            String deletedId = exerciseService.deleteUserExercise( id);
            if (deletedId == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(deletedId, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
