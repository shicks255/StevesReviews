package com.steven.hicks.services;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.dtos.SiteStats;
import com.steven.hicks.models.dtos.UserStats;
import com.steven.hicks.repositories.AlbumRepository;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.ReviewRepository;
import com.steven.hicks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class StatsService {

    @Autowired
    private AlbumRepository m_albumRepository;
    @Autowired
    private ArtistRepository m_artistRepository;
    @Autowired
    private ReviewRepository m_reviewRepository;
    @Autowired
    private UserRepository m_userRepository;

    private SiteStats instance;

    @Bean
    public SiteStats getSiteStats() {
//        if (instance == null)
//        {
            long users = m_userRepository.count();
            long albums = m_albumRepository.count();
            List<Review> reviews = m_reviewRepository.findAll();
            long ratings = reviews.stream()
                    .filter(x -> x.getRating() > 0.0)
                    .count();
            long fiveStars = reviews.stream()
                    .filter(x -> x.getRating() == 5.0)
                    .count();

            instance = new SiteStats(users, albums, reviews.size(), ratings, fiveStars);
//        }

        return instance;
    }

    public UserStats getUserStats(int userId) {

        List<Review> reviews = m_reviewRepository.findAllByUserIdOrderByAddedOnDesc(userId);
        if (reviews.size() == 0)
            return new UserStats(0, 0, 0.0f, 0, "");

        long fiveStars = reviews.stream().filter(x -> x.getRating() == 5.0).count();
        float averageReview = reviews.stream()
                .map(Review::getRating)
                .reduce(0.0f, (f1,f2) -> f1 + f2) / reviews.size();

        int averageLength = reviews.stream()
                .map(x -> x.getContent().split(" ").length)
                .reduce(0, (o,i) -> o + i) / reviews.size();


        String lastReviewDate = reviews.stream()
                .findFirst()
                .map(x -> x.getAddedOn().toLocalDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")))
                .orElse("");

        return new UserStats(reviews.size(), fiveStars, averageReview, averageLength, lastReviewDate);

    }
}
