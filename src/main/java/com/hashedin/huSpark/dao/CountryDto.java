package com.hashedin.huSpark.dao;

import lombok.Data;

@Data
public class CountryDto {
    private int id;
    private String name;

    public CountryDto(int id, String name) {
        this.id =id;
        this.name = name;
    }
}
