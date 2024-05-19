package com.hashedin.huSpark.dao;

public class ManagerUpdate {
    private Long employeeId;
    private Long managerId;

    public ManagerUpdate(Long employeeId, Long managerId) {
        this.employeeId = employeeId;
        this.managerId = managerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }



}
