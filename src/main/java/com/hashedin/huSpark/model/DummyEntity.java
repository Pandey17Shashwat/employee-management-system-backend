package com.hashedin.huSpark.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Data
@Entity
public class DummyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unique_long_seq")
    @SequenceGenerator(name = "unique_long_seq", sequenceName = "unique_long_seq", allocationSize = 1)
    private Long id;

}
