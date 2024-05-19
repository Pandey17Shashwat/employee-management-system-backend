package com.hashedin.huSpark.dao;

import com.hashedin.huSpark.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerResponseDto {
    private Long employeeId;
    private String name;
    private String email;
    private LocalDate birthDay;
    private Country country;
    private EmployeeRole role;
    private Address employeeAddress;
    private List<Department>departments;

    public ManagerResponseDto(Employee employee){
        this.employeeId= employee.getEmployeeId();
        this.name=employee.getName();
        this.email= employee.getEmail();
        this.birthDay=employee.getBirthDay();
        this.country=employee.getCountry();
        this.role=employee.getRole();
        this.employeeAddress=employee.getEmployeeAddress();
        List<EmployeeDepartmentHistory> deptHistoryList = employee.getEmployeeDepartmentHistories();

        List<EmployeeDepartmentHistory> mostRecent = deptHistoryList.stream()
                .filter(history -> history.getLeaveDate() == null).collect(Collectors.toList());
        List<Department>departments=mostRecent.stream().map(e->e.getDepartment()).collect(Collectors.toList());
        this.departments=departments;

    }
}

