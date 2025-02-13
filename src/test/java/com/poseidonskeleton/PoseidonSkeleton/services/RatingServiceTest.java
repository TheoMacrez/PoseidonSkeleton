package com.poseidonskeleton.PoseidonSkeleton.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for RatingService.
 */
public class RatingServiceTest {

    @InjectMocks
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    private Rating rating;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("A");
        rating.setSandPRating("A+");
        rating.setFitchRating("AA");
        rating.setOrderNumber(1);
    }

    /**
     * Tests the createRating method of RatingService.
     */
    @Test
    public void testCreateRating() {
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating createdRating = ratingService.createRating(rating);

        assertThat(createdRating).isEqualTo(rating);
        verify(ratingRepository, times(1)).save(rating);
    }

    /**
     * Tests the getAllRatings method of RatingService.
     */
    @Test
    public void testGetAllRatings() {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> result = ratingService.getAllRatings();

        assertThat(result).hasSize(1);
        assertThat(result).contains(rating);
    }

    /**
     * Tests the getRatingById method of RatingService.
     */
    @Test
    public void testGetRatingById() {
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.getRatingById(1);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(rating);
    }

    /**
     * Tests the updateRating method of RatingService.
     */
    @Test
    public void testUpdateRating() {
        when(ratingRepository.existsById(1)).thenReturn(true);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating updatedRating = ratingService.updateRating(1, rating);

        assertThat(updatedRating).isEqualTo(rating);
        verify(ratingRepository, times(1)).save(rating);
    }

    /**
     * Tests the deleteRating method of RatingService.
     */
    @Test
    public void testDeleteRating() {
        doNothing().when(ratingRepository).deleteById(1);

        ratingService.deleteRating(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}
