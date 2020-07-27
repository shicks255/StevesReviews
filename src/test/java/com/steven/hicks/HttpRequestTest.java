package com.steven.hicks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate m_testRestTemplate;

    @Test
    public void greeting() {
        String result = m_testRestTemplate.getForObject("http://localhost:" + port + "/", String.class);

        assertThat(result.contains("Steve's Reviews"));
    }

}
