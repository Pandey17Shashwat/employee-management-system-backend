package com.hashedin.huSpark.service;

import com.hashedin.huSpark.dao.DepartmentDao;
import com.hashedin.huSpark.dao.DepartmentResponseDto;
import com.hashedin.huSpark.dao.EmployeeResponseDTO;
import com.hashedin.huSpark.exception.ResourceNotFoundException;
import com.hashedin.huSpark.model.Department;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.EmployeeDepartmentHistory;
import com.hashedin.huSpark.repository.DepartmentRepository;
import com.hashedin.huSpark.repository.EmployeeDepartmentHistoryRepository;
import com.hashedin.huSpark.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DepartmentService {

    private static final Logger log= LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeeDepartmentHistoryRepository employeeDepartmentHistoryRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public void addDepartment(DepartmentDao departmentDao){
        Department department= new Department(null,departmentDao.getDepartmentName(),null);
        departmentRepository.save(department);
    }
    public List<EmployeeResponseDTO> getAllActiveEmployeesByDepartment(String departmentName){
        log.info("Inside getAllActiveEmployeesByDepartment method");
        Department department = departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with name " + departmentName));

        List<EmployeeDepartmentHistory> departmentHistories = employeeDepartmentHistoryRepository.findByDepartmentAndLeaveDateIsNull(department);

        List<Employee> activeEmployees = departmentHistories.stream()
                .map(EmployeeDepartmentHistory::getEmployee)
                .collect(Collectors.toList());

        return activeEmployees.stream().map(EmployeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<Department> getEmployeePastAndCurrentDepartments(Long employeeId){
        log.info("Inside getEmployeePastAndCurrentDepartments method");
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        List<EmployeeDepartmentHistory>empDepartmentHistoryList = employeeDepartmentHistoryRepository.findByEmployee(employee);
        List<Department> empDepartments = empDepartmentHistoryList.stream().
                map(EmployeeDepartmentHistory::getDepartment).collect(Collectors.toList());
        return empDepartments;

    }

    @Transactional
    public void addEmployeeToDepartment(Long employeeId, String departmentName){
        log.info("Inside addEmployeeToDepartment method");
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        Department department = departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + departmentName));
        EmployeeDepartmentHistory el = employee.getEmployeeDepartmentHistories().stream().filter(obj->obj.getLeaveDate()==null).findFirst().orElse(null);
        if(el!=null)
         removeEmployeeFromDepartment(employee.getEmployeeId(),el.getDepartment().getDepartmentName());
        EmployeeDepartmentHistory history = new EmployeeDepartmentHistory();
        history.setEmployee(employee);
        history.setDepartment(department);
        history.setJoinDate(LocalDateTime.now());
        history.setLeaveDate(null);
        employeeDepartmentHistoryRepository.save(history);
    }
    @Transactional
    public void removeEmployeeFromDepartment(Long employeeId, String departmentName){
        log.info("Inside removeEmployeeFromDepartment method");
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        Department department = departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + departmentName));

        EmployeeDepartmentHistory history = employeeDepartmentHistoryRepository.findByEmployeeAndDepartmentAndLeaveDateIsNull(employee, department)
                .orElseThrow(() -> new IllegalStateException("Employee not in department"));

        history.setLeaveDate(LocalDateTime.now());

        employeeDepartmentHistoryRepository.save(history);
    }

    public List<DepartmentResponseDto>getAllDepartments(){
        List<DepartmentResponseDto>departmentResponseDto=departmentRepository.findAll().stream().map(DepartmentResponseDto::new)
                .collect(Collectors.toList());
        return departmentResponseDto;
    }

}
