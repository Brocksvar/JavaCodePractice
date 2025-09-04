package org.example.dto;

import java.util.UUID;

public record BookDetailsDto(
        UUID id,
        String title,
        String genre,
        int year,
        AuthorDto author
) {}
