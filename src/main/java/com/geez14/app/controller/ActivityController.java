package com.geez14.app.controller;

import com.geez14.app.entities.Activity;
import com.geez14.app.entities.ActivityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(path = "/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Return specific data based on Id
    @GetMapping("/{requestId}")
    private ResponseEntity<Activity> getActivity(@PathVariable Long requestId, Principal principal) {
        Optional<Activity> data = activityRepository.findByIdAndOwner(requestId, principal.getName());
        return data.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Return List of data based on user
    @GetMapping
    private ResponseEntity<Iterable<Activity>> getActivities(Pageable pageable, Principal principal) {
        Page<Activity> page = activityRepository.findAllByOwnerIgnoreCase(principal.getName(), PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))));
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    private ResponseEntity<Void> createActivity(@RequestBody Activity activity, Principal principal, UriComponentsBuilder ucb) {
        Activity customizedActivity = new Activity(null, activity.title(), activity.description(), principal.getName());
        Activity savedActivity = activityRepository.save(customizedActivity);
        URI locator = ucb.path("/activities/{id}")
                .buildAndExpand(savedActivity.id()).toUri();
        return ResponseEntity.created(locator).build();
    }

    @DeleteMapping("/{requestId}")
    private ResponseEntity<Void> deleteActivity(@PathVariable Long requestId, Principal principal) {
        if (activityRepository.existsByIdAndOwner(requestId, principal.getName())) {
            activityRepository.deleteById(requestId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}