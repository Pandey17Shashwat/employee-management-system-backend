package com.hashedin.huSpark.controller;


import com.hashedin.huSpark.dao.DepartmentDao;
import com.hashedin.huSpark.dao.DepartmentResponseDto;
import com.hashedin.huSpark.dao.EmployeeDepartmentDao;
import com.hashedin.huSpark.dao.EmployeeResponseDTO;
import com.hashedin.huSpark.model.Department;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.EmployeeDepartmentHistory;
import com.hashedin.huSpark.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value="*")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add/department")
    public ResponseEntity<DepartmentDao> addNewDepartment(@RequestBody DepartmentDao departmentDao){
        departmentService.addDepartment(departmentDao);
        return ResponseEntity.ok(departmentDao);
    }


    @PostMapping("/add/employee/department")
    public ResponseEntity<?> addEmployeeToDepartment(@RequestBody EmployeeDepartmentDao employeeDepartmentDao){
        departmentService.addEmployeeToDepartment(employeeDepartmentDao.getEmployeeId(), employeeDepartmentDao.getDepartmentName());
        Map<String, String> response = new HashMap<>();
        response.put("status", "department added successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove/employee/department")
    public ResponseEntity<Void> removeManagerForEmployee(@RequestBody EmployeeDepartmentDao employeeDepartmentDao){
        departmentService.removeEmployeeFromDepartment(employeeDepartmentDao.getEmployeeId(),employeeDepartmentDao.getDepartmentName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departments")
    public ResponseEntity<?>getAllDepartments(){
        List<DepartmentResponseDto> departmentResponseDtoList=departmentService.getAllDepartments();
        return new ResponseEntity<>(departmentResponseDtoList,HttpStatus.OK);
    }
}
