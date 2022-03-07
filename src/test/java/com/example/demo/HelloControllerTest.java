package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void shouldGetSecret() {
        String secret = testRestTemplate.getForObject("/", String.class);
        Assertions.assertThat(secret).isEqualTo("secret");
    }

}