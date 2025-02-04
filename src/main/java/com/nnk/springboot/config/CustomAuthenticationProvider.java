package com.nnk.springboot.config;

import com.nnk.springboot.domain.UserDomain;
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

    @Autowired
    private PasswordValidationService passwordValidationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Validation du mot de passe
        if (!passwordValidationService.isValid(password)) {
            throw new AuthenticationException("Le mot de passe ne respecte pas les critères requis.") {};
        }

        // Récupérer l'utilisateur depuis la base de données
        UserDomain user = (UserDomain) userService.loadUserByUsername(username);

        // Vérifier si l'utilisateur existe
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur " + username);
        }

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Mot de passe incorrect.");
        }

        // Si tout est bon, retourner l'objet Authentication
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

