package com.beatrizsantos.adventurebook.backend.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Section(
    @NotNull
    @Positive
    Long id, 

    @NotBlank
    String text, 

    @NotNull
    SectionType type, 

    List<Option> options
) {}
