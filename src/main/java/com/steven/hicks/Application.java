package com.steven.hicks;

import com.steven.hicks.models.Review;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.repositories.ArtistRepository;
import com.steven.hicks.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ArtistRepository m_artistRepository;

    @Autowired
    private ReviewRepository m_reviewRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Artist artists = m_artistRepository.findById(73).get();
        System.out.println(artists);

        Review review = m_reviewRepository.findById(64).get();
        System.out.println(review);
    }
}
