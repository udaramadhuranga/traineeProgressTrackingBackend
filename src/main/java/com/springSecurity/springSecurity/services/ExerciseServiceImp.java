package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.Repository.ExerciseRepository;
import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ExerciseServiceImp implements ExerciseService{

    @Autowired
    ExerciseRepository exerciseRepository;
    @Override
    public Exercise addExercise(ExerciseRequest exerciseRequest) {

        Exercise exercise = new Exercise(exerciseRequest.getTitle(),exerciseRequest.getDiscription(),exerciseRequest.getTasks(),exerciseRequest.getAssignedUsers());

        return exerciseRepository.save(exercise);
    }
}
