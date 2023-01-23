package com.example.organizationservice.service.impl;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.mapper.OrganizationMapper;
import com.example.organizationservice.repository.OrganizationRepository;
import com.example.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    /**
     * @param organizationDto
     * @return
     */
    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
          //convert dto to entity
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);
        Organization savedOrganization=organizationRepository.save(organization);
        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    /**
     * @param orgCode
     * @return
     */
    @Override
    public OrganizationDto getOrganizationByCode(String orgCode) {
        Organization organization= organizationRepository.findByOrganizationCode(orgCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
