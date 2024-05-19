package com.hashedin.huSpark.dao;

import com.hashedin.huSpark.model.EmployeeDepartmentHistory;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DepartmentHistoryDao {
    private Long departmentId;
    private LocalDateTime activeFrom;
    private LocalDateTime activeTill;

    public DepartmentHistoryDao(EmployeeDepartmentHistory employeeDepartmentHistory){
        this.departmentId=employeeDepartmentHistory.getDepartment().getDepartmentId();
        this.activeFrom=employeeDepartmentHistory.getJoinDate();
        this.activeTill=employeeDepartmentHistory.getLeaveDate();
    }
}
