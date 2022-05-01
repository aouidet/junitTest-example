package com.junit.demotest.services;


import com.junit.demotest.exceptions.ResourcesNotFoundException;
import com.junit.demotest.model.Employee;
import com.junit.demotest.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employeeMock;

    @BeforeEach
    public void setup() {
        employeeMock = Employee.builder().id(1L).firstname("oussama").lastName("aouidet").email("aouidet@gmail.com").build();
        // mock repository without annotation
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    // junit test for save employee
    @Test
    @DisplayName("junit test for save employee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        // GIVEN
        employeeRepository.save(employeeMock);
        // email null
        given(employeeRepository.findEmployeeByEmail(employeeMock.getEmail())).willReturn(Optional.empty());
        // save it
        given(employeeRepository.save(employeeMock)).willReturn(employeeMock);

        // WHEN
        Employee savedEmployee = employeeService.saveEmployee(employeeMock).get();

        // THEN
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // junit test for save employee
    @Test
    @DisplayName("junit test for save employee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException() {

        // GIVEN - precondition
        given(employeeRepository.findEmployeeByEmail(employeeMock.getEmail())).willReturn(Optional.of(employeeMock));
        //given(employeeRepository.save(employeeMock));
        org.junit.jupiter.api.Assertions.assertThrows(ResourcesNotFoundException.class, () -> employeeService.saveEmployee(employeeMock));

        // WHEN
        // THEN
        verify(employeeRepository, never()).save(any(Employee.class));


    }

}
