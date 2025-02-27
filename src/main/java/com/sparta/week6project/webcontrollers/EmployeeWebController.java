package com.sparta.week6project.webcontrollers;

import com.sparta.week6project.daos.impl.*;
import com.sparta.week6project.dtos.EmployeeDTO;
import com.sparta.week6project.dtos.FullEmpInfoDTO;
import com.sparta.week6project.entities.*;
import com.sparta.week6project.repositories.DepartmentRepository;
import com.sparta.week6project.repositories.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/web/employees")
@SessionAttributes("populatedEmp")
public class EmployeeWebController {
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    DepartmentDAO departmentDAO;

    @Autowired
    DeptEmpDAO deptEmpDAO;

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    TitleDAO titleDAO;
    @Autowired
    TitleRepository titleRepository;
    @Autowired
    SalaryDAO salaryDAO;

    @ModelAttribute("populatedEmp")
    public FullEmpInfoDTO populatedEmp(){
        return new FullEmpInfoDTO();
    }

    @GetMapping("/basic/all/{pageNum}")
    public String getAllEmployees(Model model, @PathVariable int pageNum) {
        Page<Employee> employees = employeeDAO.findAllEmployees(pageNum);
        model.addAttribute("employees", employees);
        model.addAttribute("pageNum", pageNum);
        return "employee/displayAllEmployees";
    }

    @GetMapping("/basic/{id}")
    public String getEmployeeById(Model model, @PathVariable int id) {
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee", employeeDTO);
        return "employee/displayEmployee";
    }

    @GetMapping("/basic")
    public String getEmployeeByLastName(Model model, @RequestParam String lastName) {
        List<Employee> employeeList = employeeDAO.findEmployeeByLastName(lastName);
        model.addAttribute("employees", employeeList);
        return "employee/displayAllEmployeesLastName";
    }

    @GetMapping("/update/createEmployee")
    public String createEmployee(Model model) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        model.addAttribute("employee", employeeDTO);
        return "employee/createEmployeePage";
    }

    @PostMapping("/update/createSuccess")
    public String createEmployeeSuccess(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model) {
        employeeDAO.save(employeeDTO);
        return "employee/createSuccessPage";
    }

    @GetMapping("/update/createEmployeeFull")
    public String createEmployeeFull(Model model) {
        FullEmpInfoDTO fullEmpInfoDTO = new FullEmpInfoDTO();
        List<Department> departments = departmentRepository.findAll();
        List<String> titles = titleRepository.findAllDistinctTitle();
        model.addAttribute("fullEmployee", fullEmpInfoDTO);
        model.addAttribute("departments",departments);
        model.addAttribute("titles",titles);
        return "employee/createEmployeeFullPage";
    }

    @PostMapping("/update/createFullConfirm")
    public String createEmployeeFullSuccess(@ModelAttribute("employeeFull") FullEmpInfoDTO fullEmpInfoDTO,@ModelAttribute("populatedEmp") FullEmpInfoDTO populatedEmp ,Model model) {
        //Set department info
        DeptEmpId deptEmpId = new DeptEmpId();
        deptEmpId.setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        deptEmpId.setDeptNo(fullEmpInfoDTO.getDepartment().getDeptNo());
        fullEmpInfoDTO.getDepartment().setId(deptEmpId);
        fullEmpInfoDTO.getDepartment().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getDepartment().setToDate(LocalDate.of(9999,1,1));
        fullEmpInfoDTO.getDepartment().setFromDate(fullEmpInfoDTO.getEmployee().getHireDate());

        //Set salary info
        SalaryId salaryId = new SalaryId();
        salaryId.setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        salaryId.setFromDate(fullEmpInfoDTO.getEmployee().getHireDate());
        fullEmpInfoDTO.getSalary().setId(salaryId);
        fullEmpInfoDTO.getSalary().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getSalary().setToDate(LocalDate.of(9999,1,1));

        //Set title info
        fullEmpInfoDTO.getTitle().getId().setFromDate(fullEmpInfoDTO.getEmployee().getHireDate());
        fullEmpInfoDTO.getTitle().getId().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getTitle().setEmpNo(fullEmpInfoDTO.getEmployee().getId());
        fullEmpInfoDTO.getTitle().setToDate(LocalDate.of(9999,1,1));

        model.addAttribute("populatedEmp", fullEmpInfoDTO);
        model.addAttribute("employee",fullEmpInfoDTO.getEmployee());
        model.addAttribute("title",fullEmpInfoDTO.getTitle());
        model.addAttribute("salary",fullEmpInfoDTO.getSalary());
        model.addAttribute("department",fullEmpInfoDTO.getDepartment());
        model.addAttribute("departmentName",departmentRepository.findDeptNameByDeptNo(fullEmpInfoDTO.getDepartment().getDeptNo()));
        populatedEmp.setEmployee(fullEmpInfoDTO.getEmployee());
        populatedEmp.setDepartment(fullEmpInfoDTO.getDepartment());
        populatedEmp.setSalary(fullEmpInfoDTO.getSalary());
        populatedEmp.setTitle(fullEmpInfoDTO.getTitle());
        return "employee/createFullConfirmPage";
    }

    @PostMapping("/update/createFullSuccess")
    public String createEmployeeConfirm(@ModelAttribute("populatedEmp") FullEmpInfoDTO fullEmpInfoDTO) {
        System.out.println(fullEmpInfoDTO);
        employeeDAO.save(fullEmpInfoDTO.getEmployee());
        deptEmpDAO.save(fullEmpInfoDTO.getDepartment());
        titleDAO.save(fullEmpInfoDTO.getTitle());
        salaryDAO.save(fullEmpInfoDTO.getSalary());

        return "employee/createFullSuccessPage";
    }

    @GetMapping("/update/updateEmployee/{id}")
    public String updateEmployee(Model model, @PathVariable int id) {
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        model.addAttribute("employee", employeeDTO);
        return "employee/updateEmployeePage";
    }

    @PostMapping("/update/updateEmployee/updateSuccess")
    public String updateEmployeeSuccess(@ModelAttribute("employee") EmployeeDTO employeeDTO, Model model) {
        employeeDAO.save(employeeDTO);
        return "employee/updateSuccessPage";
    }

    @GetMapping("/admin/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id, Model model) {
        EmployeeDTO employeeDTO = employeeDAO.findById(id).orElse(null);
        if (employeeDTO != null) {
            employeeDAO.deleteById(id);
        }
        model.addAttribute("employee", employeeDTO);
        return "employee/deleteSuccessPage";
    }
}
