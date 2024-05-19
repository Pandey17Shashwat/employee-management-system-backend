package com.hashedin.huSpark.dao;

public class EmployeeDepartmentDao {
    private String departmentName;

    private Long employeeId;

    public EmployeeDepartmentDao(String departmentName, Long employeeId) {
        this.departmentName = departmentName;
        this.employeeId = employeeId;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }



}
