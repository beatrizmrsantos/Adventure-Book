package com.beatrizsantos.adventurebook.backend.model;

import java.util.List;

public record Book(Long id, String title, String author, String description, List<Section> sections) {}

