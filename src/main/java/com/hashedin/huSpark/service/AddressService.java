package com.hashedin.huSpark.service;

import com.hashedin.huSpark.dao.AddressUpdateDto;
import com.hashedin.huSpark.exception.ResourceNotFoundException;
import com.hashedin.huSpark.model.Address;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.repository.AddressRepository;
import com.hashedin.huSpark.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AddressService {

    private static final Logger log= LoggerFactory.getLogger(AddressService.class);

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    EmployeeRepository employeeRepository;



    public Address getCurrentEmployeeAddress(Long empId){
        log.info("Inside getCurrentEmployeeAddress method");
        Address address= addressRepository.findByEmployeeEmployeeId(empId).
                orElseThrow(()->new ResourceNotFoundException("No associated address found"));
        return address;

    }

    public void updateAddress(Long employeeId, AddressUpdateDto addressUpdateDto){
        Employee emp=employeeRepository.findByEmployeeId(employeeId).
                orElseThrow(()->new ResourceNotFoundException("No employee found with id"+employeeId));
        Address addressToUpdate = emp.getEmployeeAddress();
        if(addressToUpdate==null)
            throw new ResourceNotFoundException("No employee address found for employee with id"+employeeId);

        if(addressUpdateDto.getCity()!=null)
            addressToUpdate.setCity(addressUpdateDto.getCity());

        if(addressUpdateDto.getState()!=null)
            addressToUpdate.setState(addressToUpdate.getState());

        if(addressUpdateDto.getLocalAddress()!=null)
            addressToUpdate.setLocalAddress(addressToUpdate.getLocalAddress());

        if(addressUpdateDto.getPinCode()!=null)
            addressUpdateDto.setPinCode(addressUpdateDto.getPinCode());

        employeeRepository.save(emp);
    }
}
