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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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
        Employee savedEmployee = employeeService.saveEmployee(employeeMock);

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

    @Test
    @DisplayName("junit test for getAll employees list")
    public void shouldGetAllEmployee_whenFindAll_thenReturnEmployeesList() {

        // GIVEN - precondition
        Employee employee1 = Employee.builder().id(1L).firstname("zouhaier").lastName("aouidet").email("zouhaier@gmail.com").build();
        given(employeeRepository.findAll()).willReturn(List.of(employeeMock, employee1));

        // WHEN
        List<Employee> expectedEmployeeList = employeeService.getAllEmployee();

        // THEN
        Assertions.assertThat(expectedEmployeeList.size()).isEqualTo(2);
        Assertions.assertThat(expectedEmployeeList).isNotNull();

    }

    @Test
    @DisplayName("junit test for empty employees list")
    public void shouldGetEmptyEmployee_whenFindAll_thenReturnEmptyEmployeesList() {

        // GIVEN
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // WHEN
        List<Employee> expectedEmployeeList = employeeService.getAllEmployee();

        // THEN
        Assertions.assertThat(expectedEmployeeList).isEmpty();
        Assertions.assertThat(expectedEmployeeList.size()).isEqualTo(0);

    }


    @Test
    @DisplayName("junit test for find by id")
    public void shouldGetEmployee_whenFindById_thenReturnEmptyEObject() {

        // GIVEN
        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employeeMock));

        // WHEN
        Employee expectedEmployee = employeeService.getEmployeeById(employeeMock.getId()).get();

        // THEN
        Assertions.assertThat(expectedEmployee).isNotNull();

    }

    @Test
    @DisplayName("junit test for update employee object")
    public void givenUpdatedEmployee_whenSaveOrUpdate_thenReturnUpdatedEmployee() {

        // given - precondition or setup
        given(employeeRepository.save(employeeMock)).willReturn(employeeMock);
        employeeMock.setFirstname("Zouhaier");
        employeeMock.setEmail("zouhaier@gmail.com");

        // when - action or the behavior that going test
        Employee updatedEmployee = employeeService.updateEmployee(employeeMock);

        // then - verify the output
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(employeeMock.getFirstname()).isEqualTo("Zouhaier");
        Assertions.assertThat(employeeMock.getEmail()).isEqualTo("zouhaier@gmail.com");
    }

    @Test
    @DisplayName("junit test for delete employee object")
    public void given_when_then() {

        // given - precondition or setup

        Long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(1L);

        // when - action or the behavior that going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

}
