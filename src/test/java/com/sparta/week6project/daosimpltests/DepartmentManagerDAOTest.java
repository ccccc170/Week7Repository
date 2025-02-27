package com.sparta.week6project.daosimpltests;

import com.sparta.week6project.daos.impl.DepartmentManagerDAO;
import com.sparta.week6project.dtos.DeptManagerDTO;
import com.sparta.week6project.entities.Department;
import com.sparta.week6project.entities.DeptManager;
import com.sparta.week6project.entities.DeptManagerId;
import com.sparta.week6project.entities.Employee;
import com.sparta.week6project.mappers.impl.DeptManagerMapperImpl;
import com.sparta.week6project.repositories.DepartmentRepository;
import com.sparta.week6project.repositories.DeptManagerRepository;
import com.sparta.week6project.repositories.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class DepartmentManagerDAOTest {

    @Autowired
    private DepartmentManagerDAO departmentManagerDAO;
    @Autowired
    private DeptManagerRepository deptManagerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DeptManagerMapperImpl deptManagerMapper;

    @Test
    void findByIdTest() {
        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setDeptNo("d009");
        deptManagerId.setEmpNo(111877);

        Optional<DeptManagerDTO> result = departmentManagerDAO.findById(deptManagerId);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @Rollback
    void saveTest() {
        Optional<Employee> employee = employeeRepository.findById(10001);

        DeptManagerId deptManagerId = new DeptManagerId();
    deptManagerId.setEmpNo(111692);
    deptManagerId.setDeptNo("d009");
        Department department = new Department();
        department.setDeptName("Customer Service");
        department.setId("d009");
    DeptManagerDTO deptManagerDTO = new DeptManagerDTO();
    deptManagerDTO.setToDate(LocalDate.of(2022, 1,1));
    deptManagerDTO.setFromDate(LocalDate.of(1999,5,5));
    deptManagerDTO.setDeptNo(department.getId());
    deptManagerDTO.setId(deptManagerId);
    deptManagerDTO.setEmpNo(employee.get().getId());
    if(deptManagerRepository.findById(deptManagerId).isEmpty()) {
        DeptManagerDTO save = departmentManagerDAO.save(deptManagerDTO);
    }
    Assertions.assertTrue(departmentManagerDAO.findById(deptManagerId).isPresent());
    }
    @Test
    @Rollback
    void updateTest() {
        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setEmpNo(110085);
        deptManagerId.setDeptNo("d002");

        Department department = new Department();
        department.setDeptName("Staff");
        department.setId("d009");

        Optional<DeptManager> deptManager = deptManagerRepository.findById(deptManagerId);
        if (deptManager.isPresent()) {
            DeptManagerDTO deptManagerDTO = new DeptManagerDTO();
            deptManagerDTO.setEmpNo(employeeRepository.findById(10003).get().getId());
            deptManagerDTO.setId(deptManagerId);
            deptManagerDTO.setFromDate(LocalDate.of(1999, 1, 1));
            deptManagerDTO.setToDate(LocalDate.of(2020, 5, 7));
            deptManagerDTO.setDeptNo(department.getId());
            departmentManagerDAO.update(deptManagerDTO);
        }
        if (deptManagerRepository.findById(deptManagerId).isPresent()) {
            Optional<DeptManager> deptartmentManeger1 = deptManagerRepository.findById(deptManagerId);

            Assertions.assertEquals(deptartmentManeger1.get().getFromDate(), LocalDate.of(1999, 1, 1));
        }
    }



    @Test
    @Rollback
    void deleteByIdTest() {
        DeptManagerId deptManagerId = new DeptManagerId();
        deptManagerId.setEmpNo(110039);
        deptManagerId.setDeptNo("d001");
        departmentManagerDAO.deleteById(deptManagerId);
        Assertions.assertFalse(deptManagerRepository.findById(deptManagerId).isPresent());
    }

    @Test
    void deptManagerDAOTest() {
        DepartmentManagerDAO departmentManagerDAO1 = new DepartmentManagerDAO(deptManagerRepository, deptManagerMapper);
        Assertions.assertNotNull(departmentManagerDAO1);
    }
}