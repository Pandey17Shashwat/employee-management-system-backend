package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Optional<Employee> findByEmployeeId(Long employeeId);
    Optional<Employee> findByEmail(String email);
    List<Employee> findAllByBirthDay(LocalDate today);
    boolean existsByEmployeeId(Long employeeId);
    void deleteByEmployeeId(Long employeeId);
    @Query("SELECT e FROM Employee e WHERE "
            + "MONTH(e.birthDay) = ?1 AND DAY(e.birthDay) = ?2")
    List<Employee> findAllWithBirthday(int month, int day);

}
