package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to Rating entities.
 */
@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * Handles the request to display the list of Ratings.
     *
     * @param model the Model object to add attributes.
     * @param userDetails the details of the authenticated user.
     * @return the view name for the Rating list page.
     */
    @RequestMapping("/rating/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("ratings", ratingService.getAllRatings());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "rating/list";
    }

    /**
     * Handles the request to display the form for adding a new Rating.
     *
     * @param model the Model object to add attributes.
     * @return the view name for the add Rating form.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    /**
     * Handles the request to validate and save a new Rating.
     *
     * @param rating the Rating object to validate and save.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the Rating list page if successful, otherwise the add form.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingService.createRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * Handles the request to display the form for updating an existing Rating.
     *
     * @param id the ID of the Rating to update.
     * @param model the Model object to add attributes.
     * @return the view name for the update Rating form if the Rating exists, otherwise redirect to the list page.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.getRatingById(id);
        if (rating.isPresent()) {
            model.addAttribute("rating", rating.get());
            return "rating/update";
        }
        return "redirect:/rating/list";
    }

    /**
     * Handles the request to update an existing Rating.
     *
     * @param id the ID of the Rating to update.
     * @param rating the Rating object with updated data.
     * @param result the BindingResult object to handle validation errors.
     * @param model the Model object to add attributes.
     * @return the view name for the update Rating form if there are errors, otherwise redirect to the list page.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }
        ratingService.updateRating(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Handles the request to delete a Rating.
     *
     * @param id the ID of the Rating to delete.
     * @return the view name to redirect to the Rating list page.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
