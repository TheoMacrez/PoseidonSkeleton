package com.nnk.springboot.services;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create
    public UserDomain createUser(UserDomain user) {
        return userRepository.save(user);
    }

    // Read all
    public List<UserDomain> getAllUsers() {
        return userRepository.findAll();
    }

    // Read by ID
    public Optional<UserDomain> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Update
    public UserDomain updateUser(Integer id, UserDomain user) {
        if (userRepository.existsById(id)) {
            user.setId(id); // Set the ID for the entity to update
            return userRepository.save(user);
        }
        return null; // Or throw an exception
    }

    // Delete
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
