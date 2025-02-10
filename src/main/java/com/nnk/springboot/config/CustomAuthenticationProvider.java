package com.nnk.springboot.config;

import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.services.AuthenticationService;
import com.nnk.springboot.services.PasswordValidationService;
import com.nnk.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    private final PasswordValidationService passwordValidationService;

    private final AuthenticationService authenticationService;

    @Autowired
    public CustomAuthenticationProvider(PasswordValidationService passwordValidationService, AuthenticationService authenticationService) {
        this.passwordValidationService = passwordValidationService;
        this.authenticationService = authenticationService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (!passwordValidationService.isValid(password)) {
            throw new BadCredentialsException("Le mot de passe ne respecte pas les critères requis.");
        }
        // Authentifier l'utilisateur
        UserDomain user = authenticationService.authenticate(username, password);

        // Log des informations de l'utilisateur
        System.out.println("Utilisateur authentifié : " + user.getUsername() + ", Rôle : " + user.getRole());

        // Si tout est bon, retourner l'objet Authentication
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

