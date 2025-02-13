package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.services.PasswordValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for PasswordValidationService.
 */
public class PasswordValidationServiceTest {

    @InjectMocks
    private PasswordValidationService passwordValidationService;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the isValid method of PasswordValidationService with a valid password.
     */
    @Test
    public void testIsValidWithValidPassword() {
        String validPassword = "ValidPassword1!";
        assertTrue(passwordValidationService.isValid(validPassword));
    }

    /**
     * Tests the isValid method of PasswordValidationService with an invalid password.
     */
    @Test
    public void testIsValidWithInvalidPassword() {
        String invalidPassword = "invalid";
        assertFalse(passwordValidationService.isValid(invalidPassword));
    }

    /**
     * Tests the isValid method of PasswordValidationService with a password that is too short.
     */
    @Test
    public void testIsValidWithShortPassword() {
        String shortPassword = "Short1";
        assertFalse(passwordValidationService.isValid(shortPassword));
    }

    /**
     * Tests the isValid method of PasswordValidationService with a password that does not contain any special characters.
     */
    @Test
    public void testIsValidWithNoSpecialCharacters() {
        String noSpecialCharactersPassword = "NoSpecialCharacters1";
        assertFalse(passwordValidationService.isValid(noSpecialCharactersPassword));
    }

    /**
     * Tests the isValid method of PasswordValidationService with a password that does not contain any digits.
     */
    @Test
    public void testIsValidWithNoDigits() {
        String noDigitsPassword = "NoDigitsPassword";
        assertFalse(passwordValidationService.isValid(noDigitsPassword));
    }
}
