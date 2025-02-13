package com.nnk.springboot.services;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Service class for validating passwords.
 */
@Service
public class PasswordValidationService {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    /**
     * Validates a password against a predefined pattern.
     *
     * @param password the password to validate.
     * @return true if the password matches the pattern, otherwise false.
     */
    public boolean isValid(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }
}
