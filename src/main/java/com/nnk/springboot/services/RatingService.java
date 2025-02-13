package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for handling business logic related to Rating entities.
 */
@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Creates a new Rating.
     *
     * @param rating the Rating object to create.
     * @return the created Rating object.
     */
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    /**
     * Retrieves all Ratings.
     *
     * @return a list of all Ratings.
     */
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    /**
     * Retrieves a Rating by its ID.
     *
     * @param id the ID of the Rating to retrieve.
     * @return an Optional containing the Rating if found, otherwise empty.
     */
    public Optional<Rating> getRatingById(Integer id) {
        return ratingRepository.findById(id);
    }

    /**
     * Updates an existing Rating.
     *
     * @param id the ID of the Rating to update.
     * @param rating the Rating object with updated data.
     * @return the updated Rating object if successful, otherwise null.
     */
    public Rating updateRating(Integer id, Rating rating) {
        if (ratingRepository.existsById(id)) {
            rating.setId(id);
            return ratingRepository.save(rating);
        }
        return null;
    }

    /**
     * Deletes a Rating by its ID.
     *
     * @param id the ID of the Rating to delete.
     */
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }
}
