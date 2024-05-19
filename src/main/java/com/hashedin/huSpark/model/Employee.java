package com.hashedin.huSpark.model;

import com.fasterxml.jackson.annotation.*;
import com.hashedin.huSpark.constants.ApplicationConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee extends User{

    @Enumerated(EnumType.STRING)
    @NotEmpty(message= ApplicationConstants.ROLE_VALIDATION_MESSAGE)
    private EmployeeRole role;
    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address employeeAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    private List<EmployeeDepartmentHistory> employeeDepartmentHistories;

    @ManyToOne
    @JoinColumn(name="current_manager_id")
    private Employee currentManager;

    @OneToMany(mappedBy="currentManager")
    private List<Employee> subordinates ;

    @JsonIgnore
    @OneToMany(mappedBy="employee")
    private List<ManagerHistory> managerHistory;
}
