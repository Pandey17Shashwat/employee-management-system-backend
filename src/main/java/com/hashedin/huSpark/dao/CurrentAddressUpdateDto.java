package com.hashedin.huSpark.dao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CurrentAddressUpdateDto {
    @NotNull
    private Long empID;
    private Long newCurrentAddressId;


}
