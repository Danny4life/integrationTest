package com.osiki.integrationTesting.service;

import com.osiki.integrationTesting.entity.User;
import com.osiki.integrationTesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User registerUser(String name, String email) {
        User user = new User(name, email);
        user = userRepository.save(user);
        userRepository.flush(); // Force Hibernate to commit the transaction
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
