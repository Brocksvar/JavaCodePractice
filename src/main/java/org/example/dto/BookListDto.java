package org.example.dto;

import java.util.UUID;

public record BookListDto(
        UUID id,
        String title,
        String genre,
        int year
) {}
