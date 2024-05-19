package com.hashedin.huSpark.dao;

import com.hashedin.huSpark.model.Department;
import lombok.Data;

@Data
public class DepartmentResponseDto {
    private String departmentName;
    private Long departmentId;
    public DepartmentResponseDto(Department department){
        this.departmentId = department.getDepartmentId();
        this.departmentName=department.getDepartmentName();
    }
}
