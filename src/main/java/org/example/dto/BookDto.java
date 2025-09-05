package org.example.dto;

import java.util.UUID;

public record BookDto(
        UUID id,
        String title,
        String author,
        int publicationYear
) {
}
