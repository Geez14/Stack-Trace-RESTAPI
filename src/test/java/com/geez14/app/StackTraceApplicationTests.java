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

    @Test
    void shouldReturnActivityListWhenRequested() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("Mxtylish", "password1234").getForEntity("/activities", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        Integer activityCount = documentContext.read("$.length()");
        assertThat(activityCount).isEqualTo(3);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

        JSONArray titles = documentContext.read("$..title");
        assertThat(titles).containsExactlyInAnyOrder("Learn Arabic", "Learn English", "Learn French");

        JSONArray descriptions = documentContext.read("$..description");
        assertThat(descriptions).containsExactlyInAnyOrder("Duolingo", "Duolingo", "Duolingo");
    }

    @Test
    void shouldReturnAPageOfCashByDefaultSorting() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("Mxtylish", "password1234").getForEntity("/activities?page=0&size=3", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());

        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);

        JSONArray titles = documentContext.read("$..title");
        assertThat(titles).containsExactly("Learn Arabic", "Learn English", "Learn French");
    }

    @Test
    public void shouldReturnASortedPageOfActivityWithCustomSorting() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("Mxtylish", "password1234").getForEntity("/activities?page=0&size=3&sort=id,desc", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);

        JSONArray titles = documentContext.read("$..id");
        assertThat(titles).containsExactly(101, 100, 99);
    }

    @Test
    @DirtiesContext
    public void shouldCreateAnActivity() {
        Activity activity = new Activity(null, "Save President", "London Westminster bridge, London", null);
        HttpEntity<Activity> body = new HttpEntity<>(activity);
        ResponseEntity<Void> response = restTemplate.withBasicAuth("Mxtylish", "password1234").exchange("/activities", HttpMethod.POST, body, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
        URI locator = response.getHeaders().getLocation();
        assertThat(locator).isNotNull();

        // verify the added activity
        ResponseEntity<Activity> getResponse = restTemplate.withBasicAuth("Mxtylish", "password1234").exchange(locator.getPath(), HttpMethod.GET, HttpEntity.EMPTY, Activity.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Activity getActivity = getResponse.getBody();

        assertThat(getActivity).isNotNull();
        assertThat(getActivity.title()).isEqualTo("Save President");
        assertThat(getActivity.description()).isEqualTo("London Westminster bridge, London");
    }

    @Test
    @DirtiesContext
    public void shouldDeleteActivity() {
        ResponseEntity<Void> response = restTemplate.withBasicAuth("Mxtylish", "password1234").exchange("/activities/99", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    // Security Test -----------------------

    @Test
    public void shouldNotReturnActivityWithBadPassword() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("Mxtylish", "password").getForEntity("/activities/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void shouldNotReturnActivityWithBadUsername() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("Mxtylish1234", "password1234").getForEntity("/activities/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNull();
    }
}
