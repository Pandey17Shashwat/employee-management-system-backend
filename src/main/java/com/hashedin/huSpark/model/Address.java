package com.hashedin.huSpark.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashedin.huSpark.constants.ApplicationConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int addressId;
    @Column
    @NotBlank(message= ApplicationConstants.CITY_VALIDATION_MESSAGE)
    private String city;
    @Column
    @NotBlank(message=ApplicationConstants.STATE_VALIDATION_MESSAGE)
    private String State;
    @Column
    @NotBlank(message=ApplicationConstants.ADDRESS_FIELD_VALIDATION_ERROR)
    private String localAddress;
    @Column
    private boolean currentAddress;
    @Column
    @NotBlank(message=ApplicationConstants.PIN_CODE_VALIDATION_MESSAGE)
    private int pinCode;
    @OneToOne
    @JoinColumn(name="employee_id")
    @JsonIgnore
    private Employee employee;

}
