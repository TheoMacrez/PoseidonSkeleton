package com.poseidonskeleton.PoseidonSkeleton.controllers;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome() {
        List<Rating> ratings = Arrays.asList(new Rating(), new Rating());
        when(ratingService.getAllRatings()).thenReturn(ratings);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = ratingController.home(model, userDetails);

        assertEquals("rating/list", viewName);
        verify(model).addAttribute("ratings", ratings);
        verify(model).addAttribute("loggedInUser", "testUser");
    }

    @Test
    public void testAddRatingForm() {
        String viewName = ratingController.addRatingForm(model);

        assertEquals("rating/add", viewName);
        verify(model).addAttribute(eq("rating"), any(Rating.class));
    }

    @Test
    public void testValidateRatingSuccess() {
        Rating rating = new Rating();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ratingController.validate(rating, bindingResult, model);

        assertEquals("redirect:/rating/list", viewName);
        verify(ratingService).createRating(rating);
    }

    @Test
    public void testValidateRatingFailure() {
        Rating rating = new Rating();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ratingController.validate(rating, bindingResult, model);

        assertEquals("rating/add", viewName);
        verify(ratingService, never()).createRating(any(Rating.class));
    }

    @Test
    public void testShowUpdateForm() {
        Integer id = 1;
        Rating rating = new Rating();
        when(ratingService.getRatingById(id)).thenReturn(Optional.of(rating));

        String viewName = ratingController.showUpdateForm(id, model);

        assertEquals("rating/update", viewName);
        verify(model).addAttribute("rating", rating);
    }

    @Test
    public void testShowUpdateFormNotFound() {
        Integer id = 1;
        when(ratingService.getRatingById(id)).thenReturn(Optional.empty());

        String viewName = ratingController.showUpdateForm(id, model);

        assertEquals("redirect:/rating/list", viewName);
    }

    @Test
    public void testUpdateRatingSuccess() {
        Integer id = 1;
        Rating rating = new Rating();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = ratingController.updateRating(id, rating, bindingResult, model);

        assertEquals("redirect:/rating/list", viewName);
        verify(ratingService).updateRating(id, rating);
    }

    @Test
    public void testUpdateRatingFailure() {
        Integer id = 1;
        Rating rating = new Rating();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = ratingController.updateRating(id, rating, bindingResult, model);

        assertEquals("rating/update", viewName);
        verify(ratingService, never()).updateRating(anyInt(), any(Rating.class));
    }

    @Test
    public void testDeleteRating() {
        Integer id = 1;

        String viewName = ratingController.deleteRating(id);

        assertEquals("redirect:/rating/list", viewName);
        verify(ratingService).deleteRating(id);
    }
}
