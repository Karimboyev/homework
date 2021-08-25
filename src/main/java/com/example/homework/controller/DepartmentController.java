package com.example.homework.controller;

import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.DepartmentCreateDto;
import com.example.homework.payload.response.Data;
import com.example.homework.payload.response.DepartmentDto;
import com.example.homework.service.DepartmentService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    private ResponseEntity<?> getAll(@RequestParam int page) {
        Data<DepartmentDto> all = departmentService.getAll(page);
        if (all != null) return ResponseEntity.status(200).body(all);
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable final Long id){
        DepartmentDto byId = departmentService.getById(id);
        if(byId!=null) return ResponseEntity.status(200).body(byId);
        return ResponseEntity.status(400).build();
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody final DepartmentCreateDto dto){
        GenericDto genericDto = departmentService.create(dto);
        if(genericDto!=null) return ResponseEntity.status(200).body(genericDto);
        return ResponseEntity.status(400).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable final Long id, @RequestBody DepartmentCreateDto dto) throws JsonMappingException {
        DepartmentDto update = departmentService.update(id, dto);
        if(update!=null) return ResponseEntity.status(200).body(update);
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?>  delete(@PathVariable Long id){
        boolean delete = departmentService.delete(id);
        if(delete) return ResponseEntity.status(200).build();
        return ResponseEntity.status(400).build();
    }
}
