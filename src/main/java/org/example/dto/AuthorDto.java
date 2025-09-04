package org.example.dto;

import java.util.UUID;

public record AuthorDto(
        UUID id,
        String name,
        String biography
) {}
