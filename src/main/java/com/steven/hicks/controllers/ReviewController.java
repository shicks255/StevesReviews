package com.steven.hicks.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.steven.hicks.aspects.Logged;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.User;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.ReviewService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService m_reviewService;
    @Autowired
    JwtTokenService m_jwtTokenService;

    @GetMapping("/recent")
    @Logged
    @Timed()
    public MappingJacksonValue getRecentReview() {
        List<ReviewDTO> reviews =  m_reviewService.getRecentReviews();

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("tracks");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAll();

        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                        .addFilter("artistFilter", artistFilter)
                        .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(reviews);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/user")
    @Logged
    @Timed()
    public MappingJacksonValue getUserReviews(HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("tracks");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAll();

        List<ReviewDTO> reviews = m_reviewService.getReviewsByUser(user.getId());
        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                        .addFilter("artistFilter", artistFilter)
                        .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(reviews);
        mapping.setFilters(filters);
        return mapping;

    }

    @GetMapping("/user/{userId}")
    @Logged
    @Timed()
    public MappingJacksonValue getUserReviews(@PathVariable int userId) {
        List<ReviewDTO> reviews = m_reviewService.getReviewsByUser(userId);

        SimpleBeanPropertyFilter albumFilter = SimpleBeanPropertyFilter.serializeAllExcept("tracks");
        SimpleBeanPropertyFilter artistFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id");
        SimpleBeanPropertyFilter reviewFilter = SimpleBeanPropertyFilter.serializeAll();

        FilterProvider filters =
                new SimpleFilterProvider().addFilter("albumFilter", albumFilter)
                .addFilter("artistFilter", artistFilter)
                .addFilter("reviewFilter", reviewFilter);
        MappingJacksonValue mapping = new MappingJacksonValue(reviews);
        mapping.setFilters(filters);
        return mapping;
    }

    @PostMapping("/upsert")
    @Logged
    @Timed()
    public void updateReview(@RequestBody  Review review, HttpServletRequest request) {
        User user = m_jwtTokenService.getUserFromToken(request);
        if (review.getId() == 0)
        {
            review.setUser(user);
            review.setAddedOn(LocalDateTime.now(ZoneOffset.UTC));
            m_reviewService.saveReview(review);
        }
        else
        {
            review.setLastUpdated(LocalDateTime.now());
            m_reviewService.updateReview(review);
        }
    }
}
