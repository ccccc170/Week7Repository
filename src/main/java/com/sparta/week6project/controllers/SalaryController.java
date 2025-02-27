package com.sparta.week6project.controllers;

import com.sparta.week6project.daos.impl.SalaryDAO;
import com.sparta.week6project.dtos.SalaryDTO;
import com.sparta.week6project.entities.SalaryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {
    @Autowired
    SalaryDAO salaryDAO;

    @GetMapping("/")
    public SalaryDTO findById(@RequestBody SalaryId salaryId){
        SalaryDTO salaryDTO = null;
        System.out.println(salaryId);
        Optional<SalaryDTO> salaryDTOOptional = salaryDAO.findById(salaryId);
        if (salaryDTOOptional.isPresent()){
            salaryDTO = salaryDTOOptional.get();
        }
        System.out.println(salaryDTO);
        return salaryDTO;
    }

    @PostMapping("/")
    public SalaryDTO save(@RequestBody SalaryDTO salaryDTO){ // Note you cannot provide a salary DTO where the salaryID emp no doesn't exist in the employees table
        return salaryDAO.save(salaryDTO);
    }

    @PatchMapping("/")
    public SalaryDTO update(@RequestBody SalaryDTO salaryDTO){
        Optional<SalaryDTO> salaryDTOOptional = null;
        try {
            salaryDTOOptional = salaryDAO.findById(salaryDTO.getId());
        } catch (NoSuchElementException e){
            e.printStackTrace();
        }
        SalaryDTO updateableSalary = null;
            if (salaryDTOOptional.isPresent()) {
                updateableSalary = salaryDTOOptional.get();
                if (salaryDTO.getSalary() != null) {
                    updateableSalary.setSalary(salaryDTO.getSalary());
                }
                if (salaryDTO.getToDate() != null) {
                    updateableSalary.setToDate(salaryDTO.getToDate());
                }
                return salaryDAO.save(updateableSalary);
            }
            return new SalaryDTO();
    }

    @DeleteMapping("/")
    public void deleteById(@RequestBody SalaryId salaryId){
        salaryDAO.deleteById(salaryId);
    }

    // Advanced analytical methods

    @GetMapping("/salaryAverage/")
    public double getSalaryAverageByDepartmentNumberAndDate(@RequestParam String departmentNumber, LocalDate givenDate){
        return salaryDAO.averageSalaryForDepartmentAndDate(departmentNumber,givenDate);
    }

    @GetMapping("/salaryRange/")
    public ResponseEntity getSalaryRangeByTitleAndYear(@RequestParam String jobTitle, @RequestParam int givenYear){
        ResponseEntity returnValue= null;
        String result = salaryDAO.getSalaryRangeByJobTitleAndYear(jobTitle,givenYear);
        returnValue = new ResponseEntity<>("{\"message\":\""+result+"\"}", HttpStatus.FOUND);

        return returnValue;
    }

    @GetMapping("/salaryPayGap/")
    public ResponseEntity getGenderPayGapByDepartmentNumberAndYear(@RequestParam String departmentNumber, LocalDate givenYear){
        ResponseEntity returnValue = null;
        String result = salaryDAO.getGenderPayGap(departmentNumber,givenYear);
        returnValue = new ResponseEntity<>("{\"message\":\""+result+"\"}", HttpStatus.FOUND);
        return returnValue;
    }

}

