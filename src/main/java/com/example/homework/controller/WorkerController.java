package com.example.homework.controller;

import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.WorkerCreateDto;
import com.example.homework.payload.response.Data;
import com.example.homework.payload.response.WorkerDto;
import com.example.homework.service.WorkerService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Worker")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/all")
    private ResponseEntity<?> getAll(@RequestParam int page) {
        Data<WorkerDto> all = workerService.getAll(page);
        if (all != null) return ResponseEntity.status(200).body(all);
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable final Long id) {
        WorkerDto byId = workerService.getById(id);
        if(byId!=null) return ResponseEntity.status(200).body(byId);
        return ResponseEntity.status(400).build();
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody final WorkerCreateDto dto) {
        GenericDto genericDto = workerService.create(dto);
        if(genericDto!=null) return ResponseEntity.status(200).body(genericDto);
        return ResponseEntity.status(400).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable final Long id, @RequestBody WorkerCreateDto dto) throws JsonMappingException {
        WorkerDto update = workerService.update(id, dto);
        if(update!=null) return ResponseEntity.status(200).body(update);
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        boolean delete = workerService.delete(id);
        if(delete) return ResponseEntity.status(200).build();
        return ResponseEntity.status(400).build();
    }
}
