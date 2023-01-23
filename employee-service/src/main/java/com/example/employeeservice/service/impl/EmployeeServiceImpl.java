package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.ApiResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.dto.OrganizationDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.mapper.EmployeeMapper;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.ApiClient;
import com.example.employeeservice.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    //private final RestTemplate restTemplate;

    private final WebClient webClient;
    private final ApiClient apiClient;

    /**
     * @param employeeDto
     * @return
     */
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDto1 = EmployeeMapper.mapToDto(savedEmployee);
        return employeeDto1;
    }

    /**
     * @param employeeId
     * @return
     */
   // @CircuitBreaker(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public ApiResponseDto getEmployeeByID(Long employeeId) {
        LOGGER.info("inside getEmployeeById() method");
        Employee employee = employeeRepository.findById(employeeId).get();
        /**  ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDto.class);
                                DepartmentDto departmentDto=responseEntity.getBody();
         */
          DepartmentDto departmentDto=  webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
              .block();

        OrganizationDto organizationDto=  webClient.get()
                .uri("http://localhost:8083/api/organizations/" + employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();


        //DepartmentDto departmentDto=apiClient.getDepartmentByCode(employee.getDepartmentCode());

        EmployeeDto employeeDto = EmployeeMapper.mapToDto(employee);

        ApiResponseDto apiResponseDto=new ApiResponseDto();
                       apiResponseDto.setEmployeeDto(employeeDto);
                       apiResponseDto.setDepartmentDto(departmentDto);
                       apiResponseDto.setOrganizationDto(organizationDto);
        return apiResponseDto;
    }
    public ApiResponseDto getDefaultDepartment(Long employeeId,Exception exception) {
        LOGGER.info("inside getDefaultDepartment() method");
        Employee employee = employeeRepository.findById(employeeId).get();
        DepartmentDto departmentDto=new DepartmentDto();
                      departmentDto.setDepartmentCode("R&D");
                      departmentDto.setDepartmentDescription("Development and Research");
                      departmentDto.setDepartmentName("Development and Research");
        EmployeeDto employeeDto = EmployeeMapper.mapToDto(employee);
        ApiResponseDto apiResponseDto=new ApiResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }
}
