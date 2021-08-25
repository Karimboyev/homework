package com.example.homework.controller;

import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.AddressCreateDto;
import com.example.homework.payload.response.AddressDto;
import com.example.homework.payload.response.Data;
import com.example.homework.service.AddressService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/all")
    private ResponseEntity<?> getAll(@RequestParam int page) {
        Data<AddressDto> all = addressService.getAll(page);
        if (all != null) return ResponseEntity.status(200).body(all);
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getById(@PathVariable final Long id) {
        AddressDto byId = addressService.getById(id);
        if(byId!=null) return ResponseEntity.status(200).body(byId);
        return ResponseEntity.status(400).build();
    }

    @PostMapping
    private ResponseEntity<?> create(@RequestBody final AddressCreateDto dto) {
        GenericDto genericDto = addressService.create(dto);
        if(genericDto!=null) return ResponseEntity.status(200).body(genericDto);
        return ResponseEntity.status(400).build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> update(@PathVariable final Long id, @RequestBody AddressCreateDto dto) throws JsonMappingException {
        AddressDto update = addressService.update(id, dto);
        if(update!=null) return ResponseEntity.status(200).body(update);
        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> delete(@PathVariable Long id) {
        boolean delete = addressService.delete(id);
        if(delete) return ResponseEntity.status(200).build();
        return ResponseEntity.status(400).build();

    }
}
