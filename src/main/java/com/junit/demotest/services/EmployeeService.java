package com.junit.demotest.services;

import com.junit.demotest.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployee();

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long idEmployee);
}
