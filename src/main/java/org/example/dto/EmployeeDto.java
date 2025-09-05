package org.example.dto;

import java.util.UUID;

public record EmployeeDto(
        UUID id,
        String firstName,
        String lastName,
        String position,
        Double salary,
        DepartmentDto department
) {
}
