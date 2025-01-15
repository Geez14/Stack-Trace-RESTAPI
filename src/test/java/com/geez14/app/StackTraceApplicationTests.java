package com.geez14.app;

import com.geez14.app.entities.Activity;
import com.jayway.jsonpath.DocumentContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StackTraceApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnActivityWhenRequested() {
        ResponseEntity<Activity> response = restTemplate.getForEntity("/activities/100", Activity.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
