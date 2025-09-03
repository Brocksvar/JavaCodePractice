package org.example.repository;

import org.example.entity.Employee;
import org.example.projections.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<EmployeeProjection> findAllProjectedBy();
}
