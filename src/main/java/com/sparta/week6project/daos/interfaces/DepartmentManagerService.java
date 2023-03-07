package com.sparta.week6project.daos.interfaces;

import com.sparta.week6project.dtos.DeptManagerDTO;
import com.sparta.week6project.entities.DeptManager;
import com.sparta.week6project.entities.DeptManagerId;

import java.util.List;
import java.util.Optional;

public interface DepartmentManagerService <T> {
    Optional<T> findById(DeptManagerId id);

    List<DeptManager> findAll();

    DeptManagerDTO
    save(DeptManagerDTO e);

    void update(DeptManagerDTO e);

    void deleteById(DeptManagerId id);
}
