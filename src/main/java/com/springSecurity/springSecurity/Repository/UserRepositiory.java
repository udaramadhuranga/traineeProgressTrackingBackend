package com.springSecurity.springSecurity.Repository;

import com.springSecurity.springSecurity.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositiory extends MongoRepository<User,String> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByRolesIn(List<String> roles);

}
