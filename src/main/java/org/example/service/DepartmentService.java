package org.example.service;

import org.example.dto.DepartmentDto;
import org.example.entity.Department;
import org.example.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(Department::mapToDepartmentDto)
                .toList();
    }

    public DepartmentDto getDepartmentById(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"))
                .mapToDepartmentDto();
    }

    public DepartmentDto createDepartment(Department department) {
        return departmentRepository.save(department).mapToDepartmentDto();
    }

    public DepartmentDto updateDepartment(UUID id, Department departmentDetails) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(departmentDetails.getName());
                    return departmentRepository.save(department).mapToDepartmentDto();
                })
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }

    public void deleteDepartment(UUID id) {
        departmentRepository.deleteById(id);
    }
}
