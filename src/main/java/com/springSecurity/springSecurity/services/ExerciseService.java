package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;

import java.util.List;

public interface ExerciseService {

    public Exercise addExercise(ExerciseRequest exerciseRequest);

    public String deleteExcercise(String id);

    public Exercise updateExcercise(ExerciseRequest exerciseRequest,String id);

    public List<Exercise> getAllExcercise();

    public Exercise getExcercise(String id);


}
