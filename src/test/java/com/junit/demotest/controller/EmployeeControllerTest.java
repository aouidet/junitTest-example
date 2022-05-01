package com.junit.demotest.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.junit.demotest.model.Employee;
import com.junit.demotest.services.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    // tells spring to create a mock instance of EmployeeService and add it the application context so, that it's injected into EmployeeCOntroller
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("test unit for save entity controller")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // GIVEN - precondition or setup
        Employee employee = Employee.builder().id(1L).firstname("oussama").lastName("aouidet").email("aouidet@gmail.com").build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // WHEN - action or behavior that are going tes
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        // THEN - verify the result or output using assert statements
        response.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(employee.getFirstname()))).andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())));
    }

    @Test
    @DisplayName("junit test for getEll employee Endpoint")
    public void givenEmployeesList_whenFinAllEmployee_thenReturnEmployeeList() throws Exception {

        // GIVEN - precondition or setup
        Employee employee1 = Employee.builder().id(1L).firstname("oussama").lastName("aouidet").email("aouidet@gmail.com").build();
        Employee employee2 = Employee.builder().id(1L).firstname("oussama").lastName("aouidet").email("aouidet@gmail.com").build();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        BDDMockito.given(employeeService.getAllEmployee()).willReturn(employeeList);

        // WHEN - action or behavior that are going tes
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/getAll"));


        // THEN - verify the result or output using assert statements
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())));
    }


}
