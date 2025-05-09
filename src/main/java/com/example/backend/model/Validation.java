package com.example.backend.model;

import com.example.backend.model.enums.ValidationState;

import java.util.Objects;

public record Validation(ValidationState state, String message) {
    public Validation {
        Objects.requireNonNull(state);
        Objects.requireNonNull(message);
    }
}
