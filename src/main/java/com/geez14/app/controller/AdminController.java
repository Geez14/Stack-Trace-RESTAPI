package com.geez14.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/test")
    private ResponseEntity<String> helloWorld() {
        final String greeting = "[{\"message\":\"Hello World!\"}]";
        return ResponseEntity.ok(greeting);
    }
}
