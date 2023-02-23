package com.springSecurity.springSecurity.services;

import com.springSecurity.springSecurity.Repository.RoleRepository;
import com.springSecurity.springSecurity.Repository.UserRepositiory;
import com.springSecurity.springSecurity.models.ERole;
import com.springSecurity.springSecurity.models.Role;
import com.springSecurity.springSecurity.models.User;
import com.springSecurity.springSecurity.payload.requests.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp implements UserService {


    @Autowired
    private UserRepositiory userRepositiory;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public String deleteUserById(String id) {
        userRepositiory.deleteById(id);
        return id;
    }

    @Override
    public String deletetraineeById(String id) {
        Optional<User> user = userRepositiory.findById(id);



        if(user != null){
            Set<Role> roles;

            roles = user.get().getRoles();

            List<String> convertRole = new ArrayList<>();

            roles.forEach(role -> {convertRole.add(role.getId());});

            logger.info(roleRepository.findByName(ERole.ROLE_TRAINER).get().getId());

            logger.info(convertRole.get(0));


            if((convertRole.get(0).equals(roleRepository.findByName(ERole.ROLE_TRAINER).get().getId()) ) || (convertRole.get(0).equals(roleRepository.findByName(ERole.ROLE_ADMIN).get().getId()) )){

                return "Not valid";

            }else {

                userRepositiory.deleteById(id);

                return "valid";
            }
        }
    return "Not valid";

    }

    @Override
    public User updateUser(SignupRequest user, String id) {

        Optional<User> userData = userRepositiory.findById(id);
        if(userData.isPresent()){

            User _user = userData.get();

            Set<String> strRoles = user.getRoles();
            Set<Role> roles = new HashSet<>();


            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_TRAINEE)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);

            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;

                        case "trainer":
                            Role trainerRole = roleRepository.findByName(ERole.ROLE_TRAINER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(trainerRole);

                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_TRAINEE)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }



            if(userRepositiory.existsByEmail(user.getEmail()) || userRepositiory.existsByUsername(user.getUsername())){
                return null;
            }else {

                _user.setUsername(user.getUsername());
                _user.setRoles(roles);

                _user.setPassword(encoder.encode(user.getPassword()));
                _user.setEmail(user.getEmail());
                _user.setPhoneNo(user.getPhoneNo());
                _user.setAddress(user.getAddress());

                return userRepositiory.save(_user);

            }

        }

        return (null);
    }

    @Override
    public User updateTrainee(SignupRequest user, String id) {
        Optional<User> userData = userRepositiory.findById(id);

        if(userData.isPresent()){

            User _user = userData.get();

            Set<Role> roles;

            roles = _user.getRoles();

            List<String> convertRole = new ArrayList<>();

            roles.forEach(role -> {convertRole.add(role.getId());});

            logger.info(roleRepository.findByName(ERole.ROLE_TRAINER).get().getId());

            logger.info(convertRole.get(0));


            if((convertRole.get(0).equals(roleRepository.findByName(ERole.ROLE_TRAINER).get().getId()) ) || (convertRole.get(0).equals(roleRepository.findByName(ERole.ROLE_ADMIN).get().getId()) )){

                return null;

            }else{
                if(userRepositiory.existsByEmail(user.getEmail()) || userRepositiory.existsByUsername(user.getUsername())){
                    return null;
                }else {

                    _user.setUsername(user.getUsername());
//                _user.setRoles(user.getRoles());
//                    _user.setPassword(user.getPassword());
                    _user.setEmail(user.getEmail());
                    _user.setPhoneNo(user.getPhoneNo());
                    _user.setAddress(user.getAddress());


                    return userRepositiory.save(_user);

                }
            }

        }
        return null;
    }


    //implementation of all users getting
    @Override
    public List<User> getAllTrainers() {
        logger.info("get all trainer before calling repositoy");
        return userRepositiory.findByRolesIn(Arrays.asList(roleRepository.findByName(ERole.ROLE_TRAINER).get().getId()));
    }

    @Override
    public List<User> getAllTrainees() {
        logger.info("get all trainee before calling repositoy");
        return userRepositiory.findByRolesIn(Arrays.asList(roleRepository.findByName(ERole.ROLE_TRAINEE).get().getId()));
    }
}
