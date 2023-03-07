package com.sparta.week6project.daos.interfaces;

import com.sparta.week6project.dtos.EmployeeDTO;
import com.sparta.week6project.entities.Employee;

import java.util.List;

public interface EmployeeService extends DAO<EmployeeDTO>{
    List<Employee> findEmployeeByLastName(String lastname);

    void update(EmployeeDTO e,Integer id );
}
