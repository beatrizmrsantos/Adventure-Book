package com.beatrizsantos.adventurebook.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Option(
    @NotNull
    String description, 

    @NotNull
    @Positive
    Long gotoId, 

    @NotNull
    Consequence consequence
) {}
