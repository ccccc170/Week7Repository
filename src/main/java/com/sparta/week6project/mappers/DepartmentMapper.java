package com.sparta.week6project.mappers;

import com.sparta.week6project.dtos.DepartmentDTO;
import com.sparta.week6project.entities.Department;

public interface DepartmentMapper {

    DepartmentDTO departmentToDTO(Department department);
    Department dtoToDepartment(DepartmentDTO departmentDTO);
}
