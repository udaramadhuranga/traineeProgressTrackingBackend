package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.Repository.ExerciseRepository;
import com.springSecurity.springSecurity.Repository.UserExcerciseRepository;
import com.springSecurity.springSecurity.Repository.UserRepositiory;
import com.springSecurity.springSecurity.models.Exercise;
import com.springSecurity.springSecurity.models.User;
import com.springSecurity.springSecurity.models.UserExcercise;
import com.springSecurity.springSecurity.payload.requests.UserExcerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserExerciseServiceImpl implements UserExerciseService {


    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepositiory userRepositiory;
    @Autowired
    UserExcerciseRepository userExcerciseRepository;

    @Override
    public UserExcercise addUserCourse(UserExcerciseRequest courseRequest) {

        Optional<User> trainee = userRepositiory.findById(courseRequest.getTraineeId());


        Optional<Exercise> exercise = exerciseRepository.findById(courseRequest.getExercise());

        if(trainee.isPresent() && exercise.isPresent()) {

            UserExcercise userCourse = new UserExcercise(trainee.get(), exercise.get(), courseRequest.getAssined_Date(), courseRequest.getCompleted_Date(), courseRequest.getStatus(), courseRequest.getComment());

            return userExcerciseRepository.save(userCourse);
        }else {
            return null;
        }
    }

    public List<UserExcercise> getTraineeExercises(String id){

        return userExcerciseRepository.findByTraineeId(id);
    }

    @Override
    public UserExcercise updateUserExercise(UserExcerciseRequest userExcercise,String id) {

            Optional<UserExcercise> _userExcercise = userExcerciseRepository.findById(id);

            if (_userExcercise.isPresent()){

                _userExcercise.get().setAssined_Date(userExcercise.getAssined_Date());
                _userExcercise.get().setComment(userExcercise.getComment());
                _userExcercise.get().setStatus(userExcercise.getStatus());
                _userExcercise.get().setCompleted_Date(userExcercise.getCompleted_Date());
                return userExcerciseRepository.save(_userExcercise.get());

            }else {
                return null;
            }
    }

    public UserExcercise updateUserExerciseState(UserExcerciseRequest userExcercise,String id) {

        Optional<UserExcercise> _userExcercise = userExcerciseRepository.findById(id);

        if (_userExcercise.isPresent()){


            _userExcercise.get().setStatus(userExcercise.getStatus());
            _userExcercise.get().setCompleted_Date(userExcercise.getCompleted_Date());
            return userExcerciseRepository.save(_userExcercise.get());

        }else {
            return null;
        }
    }

    @Override
    public String deleteUserExercise(String id) {
        Optional<UserExcercise> userExcercise = userExcerciseRepository.findById(id);
        if (userExcercise.isPresent()) {
            userExcerciseRepository.deleteById(id);
            return id;
        }else {
            return null;
        }
    }
}
