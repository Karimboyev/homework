package com.example.homework.service;

import com.example.homework.domain.Address;
import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.AddressCreateDto;
import com.example.homework.payload.response.AddressDto;
import com.example.homework.payload.response.Data;
import com.example.homework.repository.AddressRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Data<AddressDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Address> all = addressRepository.findAllByDeletedFalse(pageable);
        List<AddressDto> collect = all.get()
                .map(Address::convert)
                .collect(Collectors.toList());
        return new Data<>(collect, all.getTotalElements());
    }

    public AddressDto getById(Long id) {
        Optional<Address> addressOptional = addressRepository.findByIdAndDeletedFalse(id);
        if (addressOptional.isEmpty()) return null;
        return AddressDto.builder()
                .id(addressOptional.get().getId())
                .street(addressOptional.get().getStreet())
                .homeNumber(addressOptional.get().getHomeNumber())
                .build();
    }

    public GenericDto create(AddressCreateDto dto) {

        Set<ConstraintViolation<AddressCreateDto>> violations = validator.validate(dto);
        String message = "";
        if (violations.size() > 0) {
            for (ConstraintViolation<AddressCreateDto> violation : violations) {
                message = message.concat(violation.getMessage().concat(",\n"));
            }
            throw new RuntimeException(message);
        }

        Address save = addressRepository.save(Address.builder()
                .street(dto.getStreet())
                .homeNumber(dto.getHomeNumber())
                .createDate(LocalDateTime.now())
                .build());

        return GenericDto.builder().id(save.getId()).build();

    }

    public AddressDto update(Long id, AddressCreateDto dto) throws JsonMappingException {
        Optional<Address> addressOptional = addressRepository.findByIdAndDeletedFalse(id);
        if (addressOptional.isEmpty()) return null;

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Address address = objectMapper.updateValue(addressOptional.get(), dto);
        address.setLastModifiedDate(LocalDateTime.now());
        address = addressRepository.save(address);
        return AddressDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .homeNumber(address.getHomeNumber())
                .build();
    }

    public boolean delete(Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isEmpty()) return false;

        Address address = addressOptional.get();

        if (address.isDeleted()) return false;

        address.setDeleted(true);
        address.setLastModifiedDate(LocalDateTime.now());
        addressRepository.save(address);
        return true;
    }

}
