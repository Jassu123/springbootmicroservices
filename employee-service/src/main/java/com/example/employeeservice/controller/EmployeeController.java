package com.example.employeeservice.controller;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
      @PostMapping
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<ApiResponseDto> getEmployee(@PathVariable Long employeeId) {
        ApiResponseDto getEmp = employeeService.getEmployeeByID(employeeId);
        return new ResponseEntity<>(getEmp, HttpStatus.OK);
    }
}
