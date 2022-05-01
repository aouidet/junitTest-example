package com.junit.demotest.repository;

import com.junit.demotest.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    // Junit for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // given - precondition or setup
        // when - action or the behavior that going test
        Employee savedEmployee = employeeRepository.save(employee);
        // then - verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // junit test for
    @Test
    @DisplayName("junit test for getAll employee operation")
    public void givenEmployeesList_whenFindAll_thenReturnAllEpmloyeesList() {
        // given - precondition or setup
        Employee employee1 = employee;
        Employee employee2 = Employee.builder().firstname("zouhaier").lastName("aouidet").email("zouhaier@gmail.com").build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // when - action or the behavior that going test
        List<Employee> employees = employeeRepository.findAll();

        // then - verify the output
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    // junit test for
    @Test
    @DisplayName("junit test for get employee by id operation")
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behavior that going test
        Employee returnedEmployee = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        Assertions.assertThat(returnedEmployee).isNotNull();
    }

    // junit test for
    @Test
    @DisplayName("junit test for get employee by email operation")
    public void givenEmployee_whenFindByEmail_thenEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when - action or the behavior that going test
        Employee returnedEmployee = employeeRepository.findEmployeeByEmail("aouidet@gmail.com").get();

        // then - verify the output
        Assertions.assertThat(returnedEmployee).isNotNull();
    }

    // junit test for
    @Test
    @DisplayName("junit test for updated employee operation")
    public void givenEmployeeObject_whenUpdate_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behavior that going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("aouidetoussama@gmail.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("aouidetoussama@gmail.com");
    }

    // junit test for
    @Test
    @DisplayName("junit test for delete employee operation")
    public void givenEmptyListEmployee_whenDeleteEmplyee_thenReturnEmptyList() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behavior that going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        Assertions.assertThat(employeeOptional).isEmpty();
    }

    // junit test for
    @Test
    @DisplayName("junit test for custom query using jpql with index")

    public void givenEmployeeObject_whenFindByJpqlQuery_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);
        final String expectedFirstName = "oussama";
        final String expectedLastName = "aouidet";

        // when - action or the behavior that going test
        Employee givenEmployee = employeeRepository.findByJPQL("oussama", "aouidet");

        // then - verify the output
        Assertions.assertThat(givenEmployee).isNotNull();
        Assertions.assertThat(givenEmployee.getFirstname()).isEqualTo(expectedFirstName);
        Assertions.assertThat(givenEmployee.getLastName()).isEqualTo(expectedLastName);
    }

    @Test
    @DisplayName("junit test for custom query using jpql with parameters")
    public void givenFirstNameAndLastName_whenFindByNamedParams_thenEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);
        final String expectedFirstName = "oussama";
        final String expectedLastName = "aouidet";

        // when - action or the behavior that going test
        Employee expectedEmployee = employeeRepository.findByJpqlNamedParams("oussama", "aouidet");

        // then - verify the output
        Assertions.assertThat(expectedEmployee.getFirstname()).isEqualTo(expectedFirstName);
        Assertions.assertThat(expectedEmployee.getLastName()).isEqualTo(expectedLastName);

    }

    @Test
    @DisplayName("junit test for custom query using native query with index parameters")
    public void givenEmployeeObject_whenFindByNativeSql_thenEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);
        final String expectedFirstName = "oussama";
        final String expectedLastName = "aouidet";

        // when - action or the behavior that going test
        Employee expectedEmployee = employeeRepository.findByNativeSql("oussama", "aouidet");

        // then - verify the output
        Assertions.assertThat(expectedEmployee.getFirstname()).isEqualTo(expectedFirstName);
        Assertions.assertThat(expectedEmployee.getLastName()).isEqualTo(expectedLastName);

    }

    @Test
    @DisplayName("junit test for custom query using native query with parameters")
    public void givenEmployeeObject_whenFindByNativeSqlNamedParams_thenEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);
        final String expectedFirstName = "oussama";
        final String expectedLastName = "aouidet";

        // when - action or the behavior that going test
        Employee expectedEmployee = employeeRepository.findByNativeSqlNamedIndex("oussama", "aouidet");

        // then - verify the output
        Assertions.assertThat(expectedEmployee.getFirstname()).isEqualTo(expectedFirstName);
        Assertions.assertThat(expectedEmployee.getLastName()).isEqualTo(expectedLastName);

    }

    @BeforeEach
    public void setup() {
        employee = Employee.builder().firstname("oussama").lastName("aouidet").email("aouidet@gmail.com").build();
    }

}
