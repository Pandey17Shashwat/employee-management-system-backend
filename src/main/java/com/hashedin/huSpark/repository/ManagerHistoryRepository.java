package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.ManagerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerHistoryRepository extends JpaRepository<ManagerHistory,Long> {
    List<ManagerHistory> findByEmployeeEmployeeId(Long id);
    Optional<ManagerHistory> findFirstByEmployeeOrderByToDateDesc(Employee emp);
    List<ManagerHistory>findByManagerEmployeeId(Long id);
}
