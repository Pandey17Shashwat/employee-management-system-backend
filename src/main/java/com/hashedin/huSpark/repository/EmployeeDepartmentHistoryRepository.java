package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Department;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.EmployeeDepartmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDepartmentHistoryRepository extends JpaRepository<EmployeeDepartmentHistory,Long> {
    List<EmployeeDepartmentHistory> findByDepartmentAndLeaveDateIsNull(Department department);
    List<EmployeeDepartmentHistory> findByEmployee(Employee emp);
    Optional<EmployeeDepartmentHistory> findByEmployeeAndDepartmentAndLeaveDateIsNull(Employee emp,Department dept);
}
