package com.beatrizsantos.adventurebook.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Consequence(
    @NotNull
    ConsequenceType type, 

    @NotNull
    @Positive
    Integer value, 

    @NotBlank
    String text
) {}
