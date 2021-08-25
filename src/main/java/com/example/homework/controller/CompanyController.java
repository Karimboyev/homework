package com.example.homework.controller;

import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.CompanyCreateDto;
import com.example.homework.payload.response.CompanyDto;
import com.example.homework.payload.response.Data;
import com.example.homework.service.CompanyService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    private ResponseEntity<?> getAll(@RequestParam int page) {
        Data<CompanyDto> all = companyService.getAll(page);
        if(all!=null) return ResponseEntity.status(200).body(all);
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable final Long id){
        CompanyDto byId = companyService.getById(id);
        if(byId!=null) return ResponseEntity.status(200).body(byId);
        return ResponseEntity.status(400).build();
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody final CompanyCreateDto dto){
        GenericDto genericDto = companyService.create(dto);
        if(genericDto!=null) return ResponseEntity.status(200).body(genericDto);
        return ResponseEntity.status(400).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable final Long id, @RequestBody CompanyCreateDto dto) throws JsonMappingException {
        CompanyDto update = companyService.update(id, dto);
        if(update!=null) return ResponseEntity.status(200).body(update);
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?>  delete(@PathVariable Long id){
        boolean delete = companyService.delete(id);
        if(delete) return ResponseEntity.status(200).build();
        return ResponseEntity.status(400).build();
    }
}
