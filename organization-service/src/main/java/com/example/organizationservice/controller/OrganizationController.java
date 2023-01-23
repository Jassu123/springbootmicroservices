package com.example.organizationservice.controller;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto saveOrg = organizationService.saveOrganization(organizationDto);
        return new ResponseEntity<>(saveOrg, HttpStatus.OK);
    }

    @GetMapping(value = "{orgCode}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable String orgCode) {
        OrganizationDto getOrg = organizationService.getOrganizationByCode(orgCode);
        return  ResponseEntity.ok(getOrg);
    }
}
