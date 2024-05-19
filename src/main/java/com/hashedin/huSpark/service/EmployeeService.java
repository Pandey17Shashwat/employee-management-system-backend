package com.hashedin.huSpark.service;

import com.hashedin.huSpark.dao.AddressUpdateDto;
import com.hashedin.huSpark.dao.EmployeeRequestDto;
import com.hashedin.huSpark.dao.EmployeeResponseDTO;
import com.hashedin.huSpark.dao.EmployeeUpdateDAO;
import com.hashedin.huSpark.exception.ResourceExistsException;
import com.hashedin.huSpark.exception.ResourceNotFoundException;
import com.hashedin.huSpark.model.Address;
import com.hashedin.huSpark.model.DummyEntity;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.ManagerHistory;
import com.hashedin.huSpark.repository.DummyEntityRepository;
import com.hashedin.huSpark.repository.EmployeeRepository;
import com.hashedin.huSpark.repository.ManagerHistoryRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private static final Logger log= LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    ManagerHistoryRepository managerHistoryRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Autowired
    DummyEntityRepository dummyEntityRepository;

    @CacheEvict(cacheNames="employees",allEntries = true)
    public void addEmployee(EmployeeRequestDto employeeRequestDto){
        Optional<Employee> existingEmployee= employeeRepository.findByEmail(employeeRequestDto.getEmail());
        if(existingEmployee.isPresent())
            throw new ResourceExistsException("Employee with given email already exists");
        DummyEntity dummyEntity = new DummyEntity();
        dummyEntity = dummyEntityRepository.save(dummyEntity);
        Long employeeId= dummyEntity.getId();
        Employee emp=employeeRequestDto.makeEmployee(employeeId);
        Address address = employeeRequestDto.getEmployeeAddress();
        address.setEmployee(emp);
        emp.setPassword(passwordEncoder.encode(emp.getPassword()));
        employeeRepository.save(emp);
    }


    @Cacheable(cacheNames = "employees")
    public List<EmployeeResponseDTO> getEmployees(){

        return employeeRepository.findAll().stream().map(EmployeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDTO getEmployeeByEmail(String employeeEmail){
        log.info("Inside getEmployeeByEmail method");
       Employee emp = employeeRepository.findByEmail(employeeEmail).
               orElseThrow(()->new ResourceNotFoundException("Employee not found with email"+employeeEmail));
       return new EmployeeResponseDTO(emp);

    }
    public EmployeeResponseDTO getEmployeeByEmployeeId(Long employeeId){
        log.info("Inside getEmployeeById method");
        Employee emp = employeeRepository.findByEmployeeId(employeeId).
                orElseThrow(()->new ResourceNotFoundException("EMployee not found with id"+employeeId));
        return new EmployeeResponseDTO(emp);
    }

    @Transactional
    @CacheEvict(cacheNames="employees",allEntries = true)
    public void deleteEmployee(Long id) {
        log.info("Inside deleteEmployee method");
        /*if (employeeRepository.existsByEmployeeId(id)) {
            Optional<Employee> emp= employeeRepository.findByEmployeeId(id);
            employeeRepository.deleteByEmployeeId(id);
        } else {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }*/
        Optional<Employee> employee = employeeRepository.findByEmployeeId(id);
        if(!employee.isPresent()){
            throw new ResourceNotFoundException("Employee not found with id"+id);
        }
        Employee emp =employee.get();
        List<Employee>employeesAssigned = emp.getSubordinates();
        if(employeesAssigned!=null)
        employeesAssigned.forEach(currentEmp->currentEmp.setCurrentManager(null));
        if(emp.getCurrentManager()!=null){
            emp.getCurrentManager().getSubordinates().remove(emp);
        }
        List<ManagerHistory> currentManagerHistories  = managerHistoryRepository.findByManagerEmployeeId(id);
        List<Long>managerHistoryIds = currentManagerHistories.stream().
                map(ManagerHistory::getId).
                collect(Collectors.toList());
        List<Long>subordinateIds = managerHistoryRepository.findByEmployeeEmployeeId(id).stream().
                map(ManagerHistory::getId).
                collect(Collectors.toList());
        managerHistoryRepository.deleteAllById(managerHistoryIds);
        managerHistoryRepository.deleteAllById(subordinateIds);
        employeeRepository.deleteByEmployeeId(id);
    }

    @CacheEvict(cacheNames="employees",allEntries = true)
    public void updateEmployee(EmployeeUpdateDAO employeeUpdateDao){
        Employee employee=employeeRepository.findByEmployeeId(employeeUpdateDao.getEmployeeId()).
                orElseThrow(()->new ResourceNotFoundException("Employee not found with given id"));
        if(employeeUpdateDao.getName()!=null)
            employee.setName(employeeUpdateDao.getName());

        if(employeeUpdateDao.getEmail()!=null)
            employee.setEmail(employeeUpdateDao.getEmail());
        if(employeeUpdateDao.getDateOfBirth()!=null)
            employee.setBirthDay(employeeUpdateDao.getDateOfBirth());
        Address address = employee.getEmployeeAddress();
        if(employeeUpdateDao.getAddressUpdateDto()!=null){
            AddressUpdateDto updatedAddress= employeeUpdateDao.getAddressUpdateDto();
            if(updatedAddress.getLocalAddress()!=null)
                address.setLocalAddress(updatedAddress.getLocalAddress());
            if(updatedAddress.getCity()!=null)
                address.setCity(updatedAddress.getCity());
            if(updatedAddress.getState()!=null)
                address.setState(updatedAddress.getState());
            if(updatedAddress.getPinCode()!=null)
                address.setPinCode(updatedAddress.getPinCode());
        }
        address.setEmployee(employee);
        employeeRepository.save(employee);




    }

    public List<EmployeeResponseDTO>getEmployeesByBirthday(LocalDate Birthday){
        List<EmployeeResponseDTO> emp=employeeRepository.findAllByBirthDay(Birthday)
                .stream().map(EmployeeResponseDTO::new)
                .collect(Collectors.toList());
        return emp;

    }

}
