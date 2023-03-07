package com.sparta.week6project.daos.interfaces;

import com.sparta.week6project.dtos.DepartmentDTO;

import java.util.Optional;

public interface DepartmentDAOInterface {
    Optional<DepartmentDTO> findByDept_No(String dept_no);

    DepartmentDTO save(DepartmentDTO e);

    void update(DepartmentDTO e, String id);

    void deleteById(String id);
}
