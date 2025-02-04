package com.nnk.springboot.services;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordValidationService {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public boolean isValid(String password) {
        return Pattern.matches(PASSWORD_PATTERN, password);
    }
}

