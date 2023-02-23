package com.springSecurity.springSecurity.Repository;

import com.springSecurity.springSecurity.models.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise,String> {
}
