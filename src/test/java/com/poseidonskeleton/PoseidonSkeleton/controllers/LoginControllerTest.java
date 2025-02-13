package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.UserDomain;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for LoginController.
 */
public class LoginControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginController loginController;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the login method of LoginController with a null expired parameter.
     */
    @Test
    public void testLogin() {
        ModelAndView mav = loginController.login(null);

        assertEquals("login", mav.getViewName());
        assertEquals(null, mav.getModel().get("errorMsg"));
    }

    /**
     * Tests the login method of LoginController with an expired session.
     */
    @Test
    public void testLoginWithExpiredSession() {
        ModelAndView mav = loginController.login("true");

        assertEquals("login", mav.getViewName());
        assertEquals("Votre session a expiré. Veuillez vous reconnecter.", mav.getModel().get("errorMsg"));
    }

    /**
     * Tests the getAllUserArticles method of LoginController.
     */
    @Test
    public void testGetAllUserArticles() {
        List<UserDomain> users = Arrays.asList(new UserDomain(), new UserDomain());
        when(userRepository.findAll()).thenReturn(users);

        ModelAndView mav = loginController.getAllUserArticles();

        assertEquals("user/list", mav.getViewName());
        assertEquals(users, mav.getModel().get("users"));
    }

    /**
     * Tests the error method of LoginController.
     */
    @Test
    public void testError() {
        ModelAndView mav = loginController.error();

        assertEquals("403", mav.getViewName());
        assertEquals("You are not authorized for the requested data.", mav.getModel().get("errorMsg"));
    }

    /**
     * Tests the logoutPage method of LoginController.
     */
    @Test
    public void testLogoutPage() {
        Model model = mock(Model.class);

        String viewName = loginController.logoutPage(model);

        assertEquals("login", viewName);
        verify(model).addAttribute("message", "You are disconnected.");
    }
}
