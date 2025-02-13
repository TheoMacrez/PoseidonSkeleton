package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for UserController.
 */
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserController userController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the home method of UserController.
     */
    @Test
    public void testHome() {
        List<UserDomain> users = Arrays.asList(new UserDomain(), new UserDomain());
        when(userRepository.findAll()).thenReturn(users);

        String viewName = userController.home(model);

        assertEquals("user/list", viewName);
        verify(model).addAttribute("users", users);
    }

    /**
     * Tests the addUser method of UserController.
     */
    @Test
    public void testAddUser() {
        String viewName = userController.addUser(model);

        assertEquals("user/add", viewName);
        verify(model).addAttribute(eq("user"), any(UserDomain.class));
    }

    /**
     * Tests the validate method of UserController when validation is successful.
     */
    @Test
    public void testValidateUserSuccess() {
        UserDomain user = new UserDomain();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String viewName = userController.validate(user, bindingResult, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userRepository).save(user);
        verify(model).addAttribute(eq("users"), anyList());
    }

    /**
     * Tests the validate method of UserController when validation fails.
     */
    @Test
    public void testValidateUserFailure() {
        UserDomain user = new UserDomain();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.validate(user, bindingResult, model);

        assertEquals("user/add", viewName);
        verify(userRepository, never()).save(any(UserDomain.class));
    }

    /**
     * Tests the showUpdateForm method of UserController when the user exists.
     */
    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        UserDomain user = new UserDomain();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        String viewName = userController.showUpdateForm(id, model);

        assertEquals("user/update", viewName);
        verify(model).addAttribute("user", user);
    }

    /**
     * Tests the updateUser method of UserController when validation is successful.
     */
    @Test
    public void testUpdateUser() {
        Integer id = 1;
        UserDomain user = new UserDomain();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String viewName = userController.updateUser(id, user, bindingResult, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userRepository).save(user);
        verify(model).addAttribute(eq("users"), anyList());
    }

    /**
     * Tests the deleteUser method of UserController.
     */
    @Test
    public void testDeleteUser() {
        Integer id = 1;
        UserDomain user = new UserDomain();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        String viewName = userController.deleteUser(id, model);

        assertEquals("redirect:/user/list", viewName);
        verify(userRepository).delete(user);
        verify(model).addAttribute(eq("users"), anyList());
    }
}
