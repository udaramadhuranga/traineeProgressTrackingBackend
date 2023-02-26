package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.models.UserExcercise;
import com.springSecurity.springSecurity.payload.requests.UserExcerciseRequest;

import java.util.List;

public interface UserExerciseService {
    public UserExcercise addUserCourse(UserExcerciseRequest courseRequest);
    public List<UserExcercise> getTraineeExercises(String id);

    public UserExcercise updateUserExercise(UserExcerciseRequest userExcercise,String id);

    public UserExcercise updateUserExerciseState(UserExcerciseRequest userExcercise,String id);

    public String deleteUserExercise(String id);
}
