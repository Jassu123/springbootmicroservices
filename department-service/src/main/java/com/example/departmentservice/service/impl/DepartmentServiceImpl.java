package com.example.departmentservice.service.impl;
import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.mapper.DepartmentMapper;
import com.example.departmentservice.repository.DepartmentRepository;
import com.example.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    /**
     * @param departmentDto
     * @return
     */

    private final DepartmentRepository repository;
    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        //convert department dto to department entity

        Department department= DepartmentMapper.mapToDepartmentEntity(departmentDto);
            Department savedDepartment=repository.save(department);
                DepartmentDto savedDepartmentDto=DepartmentMapper.mapToDepartmentDto(savedDepartment);
        return savedDepartmentDto;
    }

    /**
     * @param departmentCode
     * @return
     */
    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department byDepartmentCode = repository.findByDepartmentCode(departmentCode);
        DepartmentDto departmentDto=DepartmentMapper.mapToDepartmentDto(byDepartmentCode);
        return departmentDto;
    }
}
