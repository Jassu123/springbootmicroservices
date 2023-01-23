package com.example.employeeservice.mapper;

import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToDto(Employee employee){
        EmployeeDto employeeDto=new EmployeeDto(employee.getEmployeeId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode(),
                employee.getOrganizationCode());

        return employeeDto;
    }

    public static Employee mapToEntity(EmployeeDto employeeDto){
        Employee employee=new Employee(employeeDto.getEmployeeId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode(),
                employeeDto.getOrganizationCode());

        return employee;
    }
}
