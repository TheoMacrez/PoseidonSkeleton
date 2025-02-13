package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.HomeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HomeControllerTest {

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome() {
        Model model = mock(Model.class);

        String viewName = homeController.home(model);

        assertEquals("home", viewName);
        verifyNoInteractions(model); // Vérifie qu'aucune interaction n'a eu lieu avec le modèle
    }

    @Test
    public void testAdminHome() {
        Model model = mock(Model.class);

        String viewName = homeController.adminHome(model);

        assertEquals("redirect:/bidList/list", viewName);
        verifyNoInteractions(model); // Vérifie qu'aucune interaction n'a eu lieu avec le modèle
    }
}

