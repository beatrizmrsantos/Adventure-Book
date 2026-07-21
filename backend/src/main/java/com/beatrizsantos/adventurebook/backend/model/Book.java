package com.beatrizsantos.adventurebook.backend.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Book(
    @NotNull
    @Positive
    Long id, 

    @NotBlank
    String title, 

    @NotBlank
    String author, 

    @NotBlank
    String description, 

    @NotNull
    BookDifficulty difficulty,

    @NotNull
    List<BookType> types, 

    @NotNull
    List<Section> sections
) {}

