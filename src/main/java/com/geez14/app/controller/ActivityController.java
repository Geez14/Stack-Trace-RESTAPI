package com.geez14.app.controller;

import com.geez14.app.entities.Activity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/activities")
public class ActivityController {
    @GetMapping("/{requestId}")
    private ResponseEntity<Activity> getActivity(@PathVariable Long requestId) {
        return ResponseEntity.ok(new Activity(requestId, "Best Activity", "No Description", "Mxtylish"));
    }
}
