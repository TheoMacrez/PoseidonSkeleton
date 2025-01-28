package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    // Create
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Read all
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    // Read by ID
    public Optional<Rating> getRatingById(Integer id) {
        return ratingRepository.findById(id);
    }

    // Update
    public Rating updateRating(Integer id, Rating rating) {
        if (ratingRepository.existsById(id)) {
            rating.setId(id); // Set the ID for the entity to update
            return ratingRepository.save(rating);
        }
        return null; // Or throw an exception
    }

    // Delete
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }
}
