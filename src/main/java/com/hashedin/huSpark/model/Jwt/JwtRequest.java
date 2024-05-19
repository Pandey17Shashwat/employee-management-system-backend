package com.hashedin.huSpark.model.Jwt;

import lombok.Data;

@Data
public class JwtRequest {

    private String email;
    private String password;
}