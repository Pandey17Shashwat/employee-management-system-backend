package com.hashedin.huSpark.model;

import com.hashedin.huSpark.dao.CountryDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Country {
    India,
    USA,
    Japan,
    UK,
    Singapore,
    Brazil,
    Thailand,
    Bhutan,
    Russia,
    Pakistan;
    public int getId() {
        return this.ordinal();
    }
    public String getName() {
        return this.name();
    }
    public static List<CountryDto> getCountryDtoList() {
        return Stream.of(Country.values())
                .map(country -> new CountryDto(country.getId(), country.getName()))
                .collect(Collectors.toList());
    }

}
