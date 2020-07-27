package com.steven.hicks;

import com.steven.hicks.controllers.ArtistController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    private ArtistController m_artistController;

    @Test
    public void contextLoads() {
        assertThat(m_artistController).isNotNull();
    }
}
