package com.hashedin.huSpark.dao;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BirthDayRequestDto {
    @NotBlank
    LocalDate birthday;
}
