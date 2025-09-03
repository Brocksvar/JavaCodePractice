package org.example.service;

import org.example.entity.Department;
import org.example.entity.Employee;
import org.example.projections.EmployeeProjection;
import org.example.repository.DepartmentRepository;
import org.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setName("IT");
        department = departmentRepository.save(department);

        Employee e1 = new Employee();
        e1.setFirstName("Ivan");
        e1.setLastName("Petrov");
        e1.setPosition("Developer");
        e1.setSalary(1000.0);
        e1.setDepartment(department);

        Employee e2 = new Employee();
        e2.setFirstName("Anna");
        e2.setLastName("Sidorova");
        e2.setPosition("Tester");
        e2.setSalary(900.0);
        e2.setDepartment(department);

        employeeRepository.save(e1);
        employeeRepository.save(e2);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        assertThat(employees).hasSize(2);
    }

    @Test
    void testGetAllEmployeeProjections() {
        List<EmployeeProjection> projections = employeeService.getAllEmployeeProjections();
        assertThat(projections).hasSize(2);

        EmployeeProjection first = projections.get(0);
        assertThat(first.getFullName()).isIn("Ivan Petrov", "Anna Sidorova");
        assertThat(first.getDepartmentName()).isEqualTo("IT");
        assertThat(first.getPosition()).isIn("Developer", "Tester");
    }

    @Test
    void testCreateAndDeleteEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Petr");
        newEmployee.setLastName("Ivanov");
        newEmployee.setPosition("Manager");
        newEmployee.setSalary(1200.0);
        newEmployee.setDepartment(department);

        Employee saved = employeeService.createEmployee(newEmployee);
        assertThat(saved.getId()).isNotNull();

        List<Employee> all = employeeService.getAllEmployees();
        assertThat(all).hasSize(3);

        employeeService.deleteEmployee(saved.getId());
        assertThat(employeeService.getAllEmployees()).hasSize(2);
    }
}