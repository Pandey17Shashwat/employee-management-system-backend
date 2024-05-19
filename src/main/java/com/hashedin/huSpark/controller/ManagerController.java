package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.dao.EmployeeResponseDTO;
import com.hashedin.huSpark.dao.ManagerUpdate;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value="*")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @PostMapping("/allocate/manager")
    public ResponseEntity<?>setManagerForEmployee(@RequestBody ManagerUpdate managerUpdate) {
        managerService.setManagerForEmployee(managerUpdate.getEmployeeId(), managerUpdate.getManagerId());
        Map<String, String> response = new HashMap<>();
        response.put("status", "manager allocated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove/manager/{employeeId}")
    public ResponseEntity<?> removeManagerForEmployee(@PathVariable Long employeeId){
        managerService.removeManagerForEmployee(employeeId);
        Map<String, String> response = new HashMap<>();
        response.put("status", "manager removed successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
