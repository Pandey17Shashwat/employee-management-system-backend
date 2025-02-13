package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByEmployeeEmployeeId(Long id);
}
