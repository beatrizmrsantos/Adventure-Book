package com.beatrizsantos.adventurebook.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameSessionDTO {
    
    @NotNull
    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long bookId;

    @NotNull
    @Positive
    private Long currentSectionId;

    @NotNull
    private int points;
}
