package com.sparta.week6project.daos.interfaces;

import java.time.LocalDate;
import java.util.Map;

public interface DeptEmpService<T> {

    public Integer getDepartmentsCount(String departmentNo, LocalDate fromDate, LocalDate toDate);
    public Map<String, Integer> getSummary(LocalDate fromDate, LocalDate toDate);

}
