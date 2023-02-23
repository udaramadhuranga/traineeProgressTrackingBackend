package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.models.User;
import com.springSecurity.springSecurity.payload.requests.SignupRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {

    String deleteUserById(String id);

    String deletetraineeById(String id);

    public User updateUser(SignupRequest user, String id);

    public User updateTrainee(SignupRequest user,String id);

    public List<User> getAllTrainers();

    public List<User> getAllTrainees();
}
