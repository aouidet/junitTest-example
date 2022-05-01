package com.junit.demotest.services;

import com.junit.demotest.model.Employee;

import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> saveEmployee(Employee employee);
}
