package org.example.dto;

import java.util.UUID;

public record DepartmentDto(
        UUID id,
        String name
) {
}
