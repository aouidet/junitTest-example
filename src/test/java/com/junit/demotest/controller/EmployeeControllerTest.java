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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @DisplayName("")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // GIVEN - precondition or setup
        Employee employee = Employee.builder().id(1L).firstname("oussama").lastName("aouidet").email("aouidet@gmail.com").build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));

        // WHEN - action or behavior that are going tes
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/add").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        // THEN - verify the result or output using assert statements
        response.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(employee.getFirstname()))).andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())));
    }


}
