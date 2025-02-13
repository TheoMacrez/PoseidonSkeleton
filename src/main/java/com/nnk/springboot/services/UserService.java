package com.nnk.springboot.services;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import lombok.Getter;
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

/**
 * Service class for handling business logic related to UserDomain entities.
 */
@Service
@Getter
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordValidationService passwordValidationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordValidationService = passwordValidationService;
    }

    @Autowired
    private PasswordValidationService passwordValidationService;

    /**
     * Loads a user by username.
     *
     * @param username the username of the user to load.
     * @return the UserDetails object representing the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDomain> userByLogin = userRepository.findByUsername(username);
        if (userByLogin.isEmpty()) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur " + username);
        }
        return userByLogin.get();
    }

    /**
     * Saves a new user.
     *
     * @param user the UserDomain object to save.
     * @return the saved UserDomain object.
     * @throws RuntimeException if the username already exists or the password does not meet the criteria.
     */
    @Transactional
    public UserDomain saveUser(UserDomain user) {
        // Validation to ensure the user does not already exist
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Cet Username est déjà utilisé !");
        }
        // Password validation
        if (!passwordValidationService.isValid(user.getPassword())) {
            throw new RuntimeException("Le mot de passe ne respecte pas les critères requis : au moins 8 caractères, une majuscule, un chiffre et un symbole.");
        }
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users.
     */
    public List<UserDomain> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve.
     * @return an Optional containing the user if found, otherwise empty.
     */
    public Optional<UserDomain> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user.
     *
     * @param id the ID of the user to update.
     * @param user the UserDomain object with updated data.
     * @return the updated UserDomain object if successful, otherwise null.
     */
    public UserDomain updateUser(Integer id, UserDomain user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete.
     */
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
