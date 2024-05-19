package com.hashedin.huSpark.dao;

import com.hashedin.huSpark.model.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequestDto {
    private String name;
    private String email;
    private String password;
    private Country country;
    private LocalDate birthDay;
    private Address employeeAddress;
    public Employee makeEmployee(Long employeeId) {
        Employee employee = new Employee();
        employee.setName(this.getName());
        employee.setEmail(this.getEmail());
        employee.setPassword(this.password);
        employee.setBirthDay(this.getBirthDay());
        employee.setCountry(this.getCountry());
        employee.setEmployeeAddress(this.getEmployeeAddress());
        employee.setUserRole(UserRole.USER);
        employee.setRole(EmployeeRole.FULL_TIME);
        employee.setEmployeeId(employeeId);
        return employee;

    }



}
