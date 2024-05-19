package com.hashedin.huSpark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashedin.huSpark.constants.ApplicationConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@Table(name="user_table")
public abstract class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank(message= ApplicationConstants.EMPLOYEE_ID_VALIDATION_MESSAGE)
    private Long employeeId;

    @Column
    @NotBlank(message=ApplicationConstants.NAME_VALIDATION_MESSAGE)
    @Size(min=2,max=50)
    private String name;

    @Column
    @NotBlank(message=ApplicationConstants.EMAIL_VALIDATION_MESSAGE)
    @Email
    private String email;

    @Column
    @NotBlank(message=ApplicationConstants.PASSWORD_VALIDATION_MESSAGE)
    private String password;
    @Enumerated(EnumType.STRING)
    @NotEmpty(message=ApplicationConstants.USER_ROLE_VALIDATION_MESSAGE)
    private UserRole userRole;

    @Column
    @NotBlank(message=ApplicationConstants.DATE_OF_BIRTH_VALIDATION_MESSAGE)
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private Country country;
}
