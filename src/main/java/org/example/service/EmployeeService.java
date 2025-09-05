package org.example.service;

import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.projections.EmployeeProjection;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(Employee::mapToEmployeeDto)
                .toList();
    }

    public EmployeeDto getEmployeeById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"))
                .mapToEmployeeDto();
    }

    public EmployeeDto createEmployee(Employee employee) {
        return employeeRepository.save(employee).mapToEmployeeDto();
    }

    public EmployeeDto updateEmployee(UUID id, Employee employeeDetails) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(employeeDetails.getFirstName());
                    employee.setLastName(employeeDetails.getLastName());
                    employee.setPosition(employeeDetails.getPosition());
                    employee.setSalary(employeeDetails.getSalary());
                    employee.setDepartment(employeeDetails.getDepartment());
                    return employeeRepository.save(employee).mapToEmployeeDto();
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeProjection> getAllEmployeeProjections() {
        return employeeRepository.findAllProjectedBy();
    }
}
