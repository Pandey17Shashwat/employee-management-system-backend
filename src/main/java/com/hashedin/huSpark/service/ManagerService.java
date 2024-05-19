package com.hashedin.huSpark.service;

import com.hashedin.huSpark.dao.EmployeeResponseDTO;
import com.hashedin.huSpark.exception.ResourceNotFoundException;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.ManagerHistory;
import com.hashedin.huSpark.repository.EmployeeRepository;
import com.hashedin.huSpark.repository.ManagerHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ManagerService {

    private static final Logger log= LoggerFactory.getLogger(ManagerService.class);

    @Autowired
    ManagerHistoryRepository managerHistoryRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeResponseDTO getCurrentManagerByEmployeeId(Long empId){
        log.info("Inside getCurrentManagerByEmployeeId method");
        Optional<Employee> employee = employeeRepository.findByEmployeeId(empId);
        Employee emp= employee.map(Employee::getCurrentManager).orElse(null);
        if(emp!=null)
        return new EmployeeResponseDTO(emp);
        else
            return null;

    }
    public List<EmployeeResponseDTO> getAllManagersByEmployeeId(Long empId){
        log.info("Inside getAllManagersByEmployeeId method");
        List<Employee>emp= managerHistoryRepository.findByEmployeeEmployeeId(empId)
                .stream()
                .map(ManagerHistory::getManager)
                .collect(Collectors.toList());
        return emp.stream().map(EmployeeResponseDTO::new).collect(Collectors.toList());

    }

    public List<Employee> getAllEmployeesForManager(Long managerId){
        log.info("Inside getAllEmployeesForManager method");
        return employeeRepository.findByEmployeeId(managerId)
                .map(Employee::getSubordinates)
                .orElse(Collections.emptyList());

    }
    @Transactional
    public void setManagerForEmployee(Long employeeId,Long managerId){
        log.info("Inside setManagerForEmployee method");
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        Optional<Employee> manager = employeeRepository.findByEmployeeId(managerId);
        if(!manager.isPresent())throw new ResourceNotFoundException("No manager found with the provided id."+employeeId);

        if(employee.isPresent() && manager.isPresent()) {
            Employee emp = employee.get();
            emp.setCurrentManager(manager.get());
            if(emp.getCurrentManager()!=null){
                removeManagerForEmployee(emp.getEmployeeId());
            }
            LocalDateTime currentTime = LocalDateTime.now();
            Optional<ManagerHistory> lastManagerHistory = managerHistoryRepository.findFirstByEmployeeOrderByToDateDesc(emp);
            if (lastManagerHistory.isPresent()) {
                ManagerHistory managerHistory = lastManagerHistory.get();
                if(managerHistory.getToDate()==null) {
                    managerHistory.setToDate(currentTime);
                    managerHistoryRepository.save(managerHistory);
                }
            }


            ManagerHistory newManagerHistory = new ManagerHistory(null, emp, manager.get(), currentTime, null);
            managerHistoryRepository.save(newManagerHistory);


            emp.setCurrentManager(manager.get());

            manager.get().getSubordinates().add(emp);

            employeeRepository.save(emp);
        } else {
            throw new ResourceNotFoundException("No employee/manager found with the provided id."+employeeId);
        }

    }
    @Transactional
    public void removeManagerForEmployee(Long employeeId){
        log.info("Inside removeManagerForEmployee method");
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        if(!employee.isPresent()){
            throw new ResourceNotFoundException("No employee found with the provided id."+employeeId);
        }

        Employee emp = employee.get();
        if(emp.getCurrentManager()==null){
            throw new ResourceNotFoundException("No manager found for the employee id "+employeeId);
        }

        LocalDateTime currentTime = LocalDateTime.now();
        Optional<ManagerHistory> lastManagerHistory = managerHistoryRepository.findFirstByEmployeeOrderByToDateDesc(emp);
        if (lastManagerHistory.isPresent()) {
            ManagerHistory managerHistory = lastManagerHistory.get();
            managerHistory.setToDate(currentTime);
            managerHistoryRepository.save(managerHistory);
        }

        emp.getCurrentManager().getSubordinates().remove(emp);
        emp.setCurrentManager(null);

        employeeRepository.save(emp);

    }
}
