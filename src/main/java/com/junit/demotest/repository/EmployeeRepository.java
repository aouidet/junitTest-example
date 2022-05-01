package com.junit.demotest.repository;

import com.junit.demotest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByEmail(String email);

    // define custom query using jpql with index parameters
    @Query(" select e from Employee e where e.firstname = ?1 and e.lastName = ?2 ")
    Employee findByJPQL(String firstName, String lastName);

    // define custom query using jpql with params
    @Query(" select e from Employee e where e.firstname =:firstName and e.lastName =:lastName")
    Employee findByJpqlNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // define custom query using native query
    @Query(value = "select * from emplyees e where e.FIRST_NAME = ?1 and e.LAST_NAME = ?2", nativeQuery = true)
    Employee findByNativeSql(String firstName, String lastName);

    // define custom query using native query with parameters
    @Query(value = "select * from emplyees e where e.FIRST_NAME =:firstName and e.LAST_NAME =:lastName", nativeQuery = true)
    Employee findByNativeSqlNamedIndex(@Param("firstName") String firstName,@Param("lastName") String lastName);
}
