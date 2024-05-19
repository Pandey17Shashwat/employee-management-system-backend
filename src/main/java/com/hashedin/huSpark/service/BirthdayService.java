package com.hashedin.huSpark.service;


import com.hashedin.huSpark.exception.ResourceNotFoundException;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BirthdayService {

    private static final Logger log= LoggerFactory.getLogger(BirthdayService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Scheduled(cron="0 * * * * *")
    public void wishBirthday(){
        log.info("Inside wishBirthday method");
        LocalDate today = LocalDate.now();
        log.info("today's date"+today);
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDate.now().getDayOfMonth();
        List<Employee> birthdayEmployees = employeeRepository.findAllWithBirthday(currentMonth,currentDay);
        for(Employee emp:birthdayEmployees){
            System.out.println("Happy Birthday"+emp.getName());
            log.info("Happy Birthday"+emp.getName());
        }
    }

    public LocalDate getEmployeeBirthDay(Long employeeId){
        Employee employee = employeeRepository.findByEmployeeId(employeeId).
                orElseThrow(()->new ResourceNotFoundException("No employee found with id"+employeeId));
        return employee.getBirthDay();
    }

}
