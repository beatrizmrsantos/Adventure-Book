package com.beatrizsantos.adventurebook.backend.model;

public record Option(String description, Long gotoId, Consequence consequence) {

    public Option(String description, Long gotoId) {
        this(description, gotoId, null);
    }
}
