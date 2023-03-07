package com.springSecurity.springSecurity.controller;

import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;
import com.springSecurity.springSecurity.services.ExerciseService;
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
@RequestMapping("/api/tasks")
public class ExerciseController {

   @Autowired
   ExerciseService exerciseService;

    Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    /**
     * Exercise add controller
     * Post Request
     * Access allows only for admins and trainers
     * @param exerciseRequest Request body object
     * @return Exercise
     * Catch all exceptions
     */
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

    /**
     * Exercise delete controller
     * Delete Request
     * Access allows only for admins and trainers
     * @param id deleting exercise id
     * @return exercise id as a string
     * Catch all exceptions
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity <String> deleteExercise(@PathVariable String id){
        try {
            logger.info("id : "  + id);
            String deletedId = exerciseService.deleteExcercise(id);
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

    /**
     * Exercise update controller
     * Put Request
     * Access allows only for admins and trainers
     * @param exerciseRequest request body object
     * @param id updating exercise id
     * @return Exercise
     *Catch all exceptions
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    public ResponseEntity <Exercise> updateUserExercise(@RequestBody ExerciseRequest exerciseRequest, @PathVariable String id){
        try {
            Exercise exercise = exerciseService.updateExcercise(exerciseRequest, id);
            if (exercise == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(exercise, HttpStatus.OK);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all exercises controller
     * Get Request
     * Access allows to all users
     * @return List<Exercises>
     * Catch all exceptions
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('TRAINEE')")
    public ResponseEntity <List<Exercise>> getAllExercises() {
        try{
            logger.info("User-Exercise controller before calling trainee-all-courses  get ");
            return new ResponseEntity<>(exerciseService.getAllExcercise(), HttpStatus.OK);

        }catch (Exception e){

            logger.info(e.getMessage());

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or hasRole('TRAINEE')")
    public ResponseEntity <Exercise> getExercises(String id) {
        try{
            logger.info("User-Exercise controller before calling trainee-all-courses  get ");
            return new ResponseEntity<>(exerciseService.getExcercise(id), HttpStatus.OK);
        }catch (Exception e){
            logger.info(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
