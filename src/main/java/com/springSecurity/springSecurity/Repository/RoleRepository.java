package com.springSecurity.springSecurity.Repository;

import com.springSecurity.springSecurity.models.ERole;
import com.springSecurity.springSecurity.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {

    Optional<Role> findByName(ERole name);
}
