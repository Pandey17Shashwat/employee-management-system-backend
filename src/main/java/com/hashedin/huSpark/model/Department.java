package com.hashedin.huSpark.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashedin.huSpark.constants.ApplicationConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long departmentId;
    @Column
    @NotBlank(message= ApplicationConstants.DEPARTMENT_NAME_VALIDATION_MESSAGE)
    private String departmentName;
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<EmployeeDepartmentHistory> employeeDepartmentHistories;

}
