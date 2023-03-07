package com.sparta.week6project.mappers;



import com.sparta.week6project.dtos.SalaryDTO;
import com.sparta.week6project.entities.Salary;

public interface SalaryMapper {
    SalaryDTO salaryToDTO(Salary salary);
    Salary dtoToSalary(SalaryDTO salaryDTO);
}
