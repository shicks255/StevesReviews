package com.steven.hicks.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.steven.hicks.models.Review;
import com.steven.hicks.models.User;
import com.steven.hicks.models.album.Album;
import com.steven.hicks.models.artist.Artist;
import com.steven.hicks.models.dtos.ReviewDTO;
import com.steven.hicks.services.AlbumService;
import com.steven.hicks.services.JwtTokenService;
import com.steven.hicks.services.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.ResultMatcher.matchAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc m_mockMvc;

    @MockBean
    private AlbumService m_albumService;

    @MockBean
    private JwtTokenService m_jwtTokenService;

    @MockBean
    private UserDetailsService m_userDetailsService;

    @Autowired
    private ObjectMapper m_objectMapper;

    @Test
    public void shouldReturnTopRated() throws Exception {

        List<ReviewDTO> reviewDTOList = Arrays.asList(getReviewDTO());
        when(m_albumService.getTopRated())
                .thenReturn(reviewDTOList);

        String json = m_objectMapper.writeValueAsString(reviewDTOList);

        m_mockMvc
                .perform(get("/api/album/topRated"))
                .andDo(print())
                .andExpect(
                        matchAll(
                                status().isOk(),
                                content().contentType(APPLICATION_JSON),
                                content().json(json)
                        ));
    }

    @Test
    public void shouldGetAlbum() {

    }

    private static ReviewDTO getReviewDTO() {
        ReviewDTO dto = new ReviewDTO(getReview());

        return dto;
    }

    private static Review getReview() {
        Review review = new Review();
        review.setLastUpdated(LocalDateTime.now());
        review.setAddedOn(LocalDateTime.now());
        review.setContent("This is a review");
        review.setId(1);
        review.setRating(1.0f);
        review.setAlbum(getAlbum());

        return review;
    }

    private static Album getAlbum() {
        Album album = new Album();
        album.setArtist(getArtist());

        return album;
    }

    private static Artist getArtist() {
        Artist artist = new Artist();
        artist.setName("Pink Floyd");

        return artist;
    }

    private static User getUser() {
        User user = new User();
        user.setPassword("password");
        user.setUsername("username");
        user.setEmailAddress("user@user.com");

        return user;
    }
}