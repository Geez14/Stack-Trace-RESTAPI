package com.geez14.app.entities;

import org.springframework.data.annotation.Id;

public record Activity(@Id Long id, String title, String description, String owner) {
}