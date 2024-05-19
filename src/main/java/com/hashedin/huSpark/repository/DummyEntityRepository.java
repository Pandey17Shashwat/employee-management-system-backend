package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DummyEntityRepository extends JpaRepository<DummyEntity,Long> {
}
