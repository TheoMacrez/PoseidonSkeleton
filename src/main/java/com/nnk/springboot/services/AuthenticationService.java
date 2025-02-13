package com.nnk.springboot.services;

import com.nnk.springboot.domain.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class to handle authentication logic.
 */
@Service
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor to initialize the AuthenticationService.
     *
     * @param userService the UserService to use.
     * @param passwordEncoder the PasswordEncoder to use.
     */
    @Autowired
    public AuthenticationService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates the user based on the provided username and password.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return a UserDomain object if authentication is successful.
     * @throws UsernameNotFoundException if the user is not found.
     * @throws BadCredentialsException if the password is incorrect.
     */
    public UserDomain authenticate(String username, String password) {
        UserDomain user = (UserDomain) userService.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur " + username);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Mot de passe incorrect.");
        }
        return user;
    }
}
