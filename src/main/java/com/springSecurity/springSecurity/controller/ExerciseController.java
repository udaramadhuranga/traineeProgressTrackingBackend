package com.springSecurity.springSecurity.controller;

import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;
import com.springSecurity.springSecurity.payload.requests.SignupRequest;
import com.springSecurity.springSecurity.services.ExerciseService;
import com.springSecurity.springSecurity.services.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tasks")
public class ExerciseController {

   @Autowired
   ExerciseService exerciseService;

    Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity<Exercise> addExercise(@RequestBody ExerciseRequest exerciseRequest) {

        try{


            return new ResponseEntity<>(exerciseService.addExercise(exerciseRequest), HttpStatus.CREATED);

        }catch (Exception e){

            logger.info(""+e);

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
