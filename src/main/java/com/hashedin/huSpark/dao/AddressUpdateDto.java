package com.hashedin.huSpark.dao;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;



@Data

public class AddressUpdateDto {

    @NotBlank
    private Long addressId;
    private String city;
    private String state;
    private String localAddress;
    private Integer pinCode;

}
