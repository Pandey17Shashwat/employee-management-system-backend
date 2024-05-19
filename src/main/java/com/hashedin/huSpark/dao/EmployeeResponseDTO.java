package com.hashedin.huSpark.dao;
import com.hashedin.huSpark.model.*;

import lombok.Data;



import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Data

public class EmployeeResponseDTO {

    private Long employeeId;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private CountryDto country;
    private EmployeeRole role;
    private Address employeeAddress;
    private Long  managerId;
    private UserRole userRole;
    private List<DepartmentHistoryDao>employeeDepartmentHistory;

    public EmployeeResponseDTO(Employee employee) {

        this.employeeId = employee.getEmployeeId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.dateOfBirth= employee.getBirthDay();
        this.country= new CountryDto(employee.getCountry().getId(), employee.getCountry().getName());
        this.role = employee.getRole();
        this.employeeAddress=employee.getEmployeeAddress();
        this.userRole= employee.getUserRole();
        if(employee.getCurrentManager()!=null)
           this.managerId=employee.getCurrentManager().getEmployeeId();
        else
            this.managerId=null;
        List<DepartmentHistoryDao> deptHistoryList = employee.getEmployeeDepartmentHistories().stream()
                .map(e-> new DepartmentHistoryDao(e)).collect(Collectors.toList());

        this.employeeDepartmentHistory=deptHistoryList;

    }



}
