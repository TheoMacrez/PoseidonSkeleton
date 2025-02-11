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

@Controller
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("ratings", ratingService.getAllRatings());
        if (userDetails != null) {
            model.addAttribute("loggedInUser", userDetails.getUsername());
        }
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating()); // Ajouter un nouvel objet Rating au modèle
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add"; // Retourner au formulaire en cas d'erreur
        }
        ratingService.createRating(rating); // Sauvegarder l'évaluation
        return "redirect:/rating/list"; // Rediriger vers la liste après ajout
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.getRatingById(id);
        if (rating.isPresent()) {
            model.addAttribute("rating", rating.get()); // Ajouter l'évaluation au modèle
            return "rating/update"; // Afficher le formulaire de mise à jour
        }
        return "redirect:/rating/list"; // Rediriger si l'évaluation n'existe pas
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id); // Assurez-vous que l'ID est correct
            return "rating/update"; // Retourner au formulaire en cas d'erreur
        }
        ratingService.updateRating(id, rating); // Mettre à jour l'évaluation
        return "redirect:/rating/list"; // Rediriger vers la liste après mise à jour
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.deleteRating(id); // Supprimer l'évaluation
        return "redirect:/rating/list"; // Rediriger vers la liste après suppression
    }
}
