package com.nnk.springboot.config;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.services.AuthenticationService;
import com.nnk.springboot.services.PasswordValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Custom implementation of AuthenticationProvider to handle authentication logic.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordValidationService passwordValidationService;
    private final AuthenticationService authenticationService;

    /**
     * Constructor to initialize the CustomAuthenticationProvider.
     *
     * @param passwordValidationService the PasswordValidationService to use.
     * @param authenticationService the AuthenticationService to use.
     */
    @Autowired
    public CustomAuthenticationProvider(PasswordValidationService passwordValidationService, AuthenticationService authenticationService) {
        this.passwordValidationService = passwordValidationService;
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates the user based on the provided credentials.
     *
     * @param authentication the Authentication object.
     * @return an Authentication object if authentication is successful.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (!passwordValidationService.isValid(password)) {
            throw new BadCredentialsException("Le mot de passe ne respecte pas les critères requis.");
        }

        UserDomain user = authenticationService.authenticate(username, password);

        System.out.println("Utilisateur authentifié : " + user.getUsername() + ", Rôle : " + user.getRole());

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    /**
     * Checks if the AuthenticationProvider supports the given Authentication class.
     *
     * @param authentication the Authentication class to check.
     * @return true if the Authentication class is supported, false otherwise.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
