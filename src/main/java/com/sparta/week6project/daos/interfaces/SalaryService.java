package com.sparta.week6project.daos.interfaces;

import com.sparta.week6project.dtos.SalaryDTO;
import com.sparta.week6project.entities.SalaryId;

import java.util.Optional;

public interface SalaryService<T> {
    Optional<T> findById(SalaryId id);

    SalaryDTO save(SalaryDTO e);

    void update(SalaryDTO e);

    void deleteById(SalaryId id);

}
