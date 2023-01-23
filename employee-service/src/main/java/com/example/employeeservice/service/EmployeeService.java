package com.example.employeeservice.service;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    ApiResponseDto getEmployeeByID(Long employeeId);
}
