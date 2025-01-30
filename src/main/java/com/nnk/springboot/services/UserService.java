package com.nnk.springboot.services;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDomain> userByLogin = userRepository.findByUsername(username);
        if (userByLogin.isEmpty()) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur " + username);
        }
        return userByLogin.map(userModel -> User.builder()
                .username(userModel.getUsername())
                .password(userModel.getPassword())
                .build()).orElse(null);
    }



    // Create and save
    @Transactional
    public UserDomain saveUser(UserDomain user) {
        // Validation pour s'assurer que l'utilisateur n'existe pas déjà
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Cet Username est déjà utilisé !");
        }
        // Hachage du mot de passe avant de le sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
