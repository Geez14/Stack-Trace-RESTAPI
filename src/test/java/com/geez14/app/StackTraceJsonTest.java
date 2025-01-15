package com.geez14.app;

import com.geez14.app.entities.Activity;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class StackTraceJsonTest {

    @Autowired
    JacksonTester<Activity> json;
    @Autowired
    JacksonTester<Activity[]> jsonList;
    private Activity[] activities;

    @BeforeEach
    void setUp() {
        activities = Arrays.array(new Activity(100L, "First Activity", "Some Common Description1", "Mxtylish"), new Activity(101L, "Second Activity", "Some Common Description2", "Mxtylish"), new Activity(102L, "Third Activity", "Some Common Description3", "Mxtylish"));
    }

    @Test
    void activitySerializationTest() throws IOException {
        Activity activity = activities[0];
        // match with file
        assertThat(json.write(activity)).isStrictlyEqualToJson("expected.json");

        // check weather the values are same or not
        assertThat(json.write(activity)).extractingJsonPathValue("$.id").isEqualTo(100);
        assertThat(json.write(activity)).extractingJsonPathValue("$.title").isEqualTo("First Activity");
        assertThat(json.write(activity)).extractingJsonPathValue("$.description").isEqualTo("Some Common Description1");
        assertThat(json.write(activity)).extractingJsonPathValue("$.owner").isEqualTo("Mxtylish");
    }

    @Test
    void activityDeserializationTest() throws IOException {
        String expectedJson = """
                {
                "id" : 100,
                "title": "First Activity",
                "description": "Some Common Description1",
                "owner": "Mxtylish"
                }
                """;
        // check weather json matches to the object
        assertThat(json.parse(expectedJson)).isEqualTo(activities[0]);
        Activity out = json.parseObject(expectedJson);
        assertThat(out.id()).isEqualTo(100);
        assertThat(out.title()).isEqualTo("First Activity");
        assertThat(out.description()).isEqualTo("Some Common Description1");
        assertThat(out.owner()).isEqualTo("Mxtylish");
    }

    @Test
    void activityListSerializationTest() throws IOException {
        assertThat(jsonList.write(activities)).isEqualToJson("list.json");
    }

    @Test
    void activityListDeserializationTest() throws IOException {
        String expected = """
                                [
                  {
                    "id": 100,
                    "title": "First Activity",
                    "description": "Some Common Description1",
                    "owner": "Mxtylish"
                  },
                  {
                    "id": 101,
                    "title": "Second Activity",
                    "description": "Some Common Description2",
                    "owner": "Mxtylish"
                  },
                  {
                    "id": 102,
                    "title": "Third Activity",
                    "description": "Some Common Description3",
                    "owner": "Mxtylish"
                  }
                ]
                """;
        assertThat(jsonList.parse(expected)).isEqualTo(activities);
    }
}
