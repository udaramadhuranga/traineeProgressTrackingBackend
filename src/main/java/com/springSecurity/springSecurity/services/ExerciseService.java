package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;

public interface ExerciseService {

    public Exercise addExercise(ExerciseRequest exerciseRequest);


}
