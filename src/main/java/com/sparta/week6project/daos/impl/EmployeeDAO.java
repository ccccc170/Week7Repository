package com.sparta.week6project.daos.impl;

import com.sparta.week6project.daos.interfaces.EmployeeService;
import com.sparta.week6project.dtos.EmployeeDTO;
import com.sparta.week6project.entities.Employee;
import com.sparta.week6project.mappers.impl.EmployeeMapperImpl;
import com.sparta.week6project.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@EnableAutoConfiguration
public class EmployeeDAO implements EmployeeService {

    @Autowired
    private EmployeeMapperImpl employeeMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDAO(EmployeeMapperImpl employeeMapper, EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDAO() {
    }

    @Override
    public Optional<EmployeeDTO> findById(Integer id) {
        return Optional.of(employeeMapper.employeeToDto(employeeRepository.findById(id).get()));
    }

    public Page<Employee> findAllEmployees(int pageNum){
        return employeePage(PageRequest.of(pageNum-1,50));
    }

    public Page<Employee> employeePage(Pageable pageable){return employeeRepository.findAll(pageable);}

    @Override
    public EmployeeDTO save(EmployeeDTO e) {
        return employeeMapper.employeeToDto(employeeRepository.save(employeeMapper.dtoToEmployee(e)));
    }


    @Override
    public void update(EmployeeDTO e, Integer id) {
        Optional<Employee> employeeDb = employeeRepository.findById(id);
        if(employeeDb.isPresent()) {
            Employee existingEmployee = employeeDb.get();
            existingEmployee.setFirstName(e.getFirstName());
            existingEmployee.setLastName(e.getLastName());
            existingEmployee.setGender(e.getGender());
            existingEmployee.setBirthDate(e.getBirthDate());
            existingEmployee.setHireDate(e.getHireDate());
            employeeRepository.save(existingEmployee);
        }
    }

    @Override
    public void deleteById(Integer id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findEmployeeByLastName(String lastname) {
//        return employeeRepository.findAll()
//                .stream()
//                .filter(a->a.getLastName().equals(lastname))
//                        .collect(Collectors.toList());
        return employeeRepository.findAllBylastName(lastname);
    }

}

