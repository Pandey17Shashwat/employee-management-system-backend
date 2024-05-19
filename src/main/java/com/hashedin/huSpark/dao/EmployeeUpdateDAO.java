package com.hashedin.huSpark.dao;

import com.hashedin.huSpark.constants.ApplicationConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeUpdateDAO {
    @NotBlank(message= ApplicationConstants.EMPLOYEE_ID_VALIDATION_MESSAGE)
    private Long employeeId;
    private String email;
    private String name;
    private LocalDate dateOfBirth;
    private AddressUpdateDto addressUpdateDto;
}
