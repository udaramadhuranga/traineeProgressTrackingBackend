package com.springSecurity.springSecurity.Repository;


import com.springSecurity.springSecurity.models.UserExcercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExcerciseRepository extends MongoRepository<UserExcercise,String> {

    @Query("{ 'traineeId.id' : ?0 }")
    List<UserExcercise> findByTraineeId(String id);
}
