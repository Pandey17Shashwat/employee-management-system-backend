package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.dao.BirthDayRequestDto;
import com.hashedin.huSpark.dao.EmployeeRequestDto;
import com.hashedin.huSpark.dao.EmployeeResponseDTO;
import com.hashedin.huSpark.dao.EmployeeUpdateDAO;
import com.hashedin.huSpark.model.Address;
import com.hashedin.huSpark.model.Country;
import com.hashedin.huSpark.model.Employee;
import com.hashedin.huSpark.model.Jwt.JwtRequest;
import com.hashedin.huSpark.model.Jwt.JwtResponse;
import com.hashedin.huSpark.service.BirthdayService;
import com.hashedin.huSpark.service.EmployeeService;
import com.hashedin.huSpark.service.UserAuthDetailService;
import com.hashedin.huSpark.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/api")
@CrossOrigin(value="*")
public class EmployeeController {

    private static final Logger log= LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService employeeService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    BirthdayService birthdayService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserAuthDetailService userAuthDetailService;

    @PostMapping(value="/employee")
    public ResponseEntity<Object> addUser(@RequestBody EmployeeRequestDto employeeRequestDto){
        try{
          employeeService.addEmployee(employeeRequestDto);
            Map<String, String> response = new HashMap<>();
            response.put("status", "User added successfully");
          return new ResponseEntity<>(response,HttpStatus.OK);
       }
        catch(Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }



    @GetMapping(value="/employees")
    public ResponseEntity<Object> getUsers(){
        try{
            return new ResponseEntity<>(employeeService.getEmployees(),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/employee")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeUpdateDAO employeeUpdateDAO){
        employeeService.updateEmployee(employeeUpdateDAO);
        Map<String, String> response = new HashMap<>();
        response.put("status", "User updated successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateEmployee(@RequestBody JwtRequest authenticationRequest) throws Exception {
        log.info("here");
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userAuthDetailService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtUtils.generateJwtToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        catch (Exception e){
            log.error(e.toString());
        }
    }


    @GetMapping("/countries")
    public ResponseEntity<Object> getAllLocations(){
        return new ResponseEntity<>(Country.getCountryDtoList(),HttpStatus.OK);

    }


}
