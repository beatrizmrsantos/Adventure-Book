package com.beatrizsantos.adventurebook.backend.model;

import java.util.List;

public record Section(Long id, String text, SectionType type, List<Option> options) {}
