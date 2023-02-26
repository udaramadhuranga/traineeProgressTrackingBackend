package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.Repository.ExerciseRepository;
import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.payload.requests.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExerciseServiceImp implements ExerciseService{

    @Autowired
    ExerciseRepository exerciseRepository;
    @Override
    public Exercise addExercise(ExerciseRequest exerciseRequest) {

        Exercise exercise = new Exercise(exerciseRequest.getTitle(),exerciseRequest.getDescription(),exerciseRequest.getTasks(),exerciseRequest.getMaximum_time());

        return exerciseRepository.save(exercise);
    }

    @Override
    public String deleteExcercise(String id) {
        exerciseRepository.deleteById(id);
        return id;
    }

    @Override
    public Exercise updateExcercise(ExerciseRequest exerciseRequest, String id) {
     Exercise _exerciseRequest  =exerciseRepository.findById(id).get();

        _exerciseRequest.setDescription(exerciseRequest.getDescription());
        _exerciseRequest.setTasks(exerciseRequest.getTasks());
        _exerciseRequest.setTitle(exerciseRequest.getTitle());
        _exerciseRequest.setMaximum_time(exerciseRequest.getMaximum_time());

        return _exerciseRequest;

    }

    @Override
    public List<Exercise> getAllExcercise() {
        return exerciseRepository.findAll();
    }
}
