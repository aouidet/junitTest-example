package com.junit.demotest.services;

import com.junit.demotest.exceptions.ResourcesNotFoundException;
import com.junit.demotest.model.Employee;
import com.junit.demotest.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Service
@RequestScope
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> saveEmployee(Employee employee) {

        Optional<Employee> savedEmployee = employeeRepository.findEmployeeByEmail(employee.getEmail());

        if(savedEmployee.isPresent()) {
            throw new ResourcesNotFoundException("tEmployee already exist with given email : "+ employee.getEmail());
        }
        return Optional.ofNullable(employeeRepository.save(employee));
    }
}
