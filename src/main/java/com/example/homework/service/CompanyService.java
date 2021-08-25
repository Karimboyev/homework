package com.example.homework.service;

import com.example.homework.domain.Address;
import com.example.homework.domain.Company;
import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.CompanyCreateDto;
import com.example.homework.payload.request.CompanyUpdateDto;
import com.example.homework.payload.response.CompanyDto;
import com.example.homework.payload.response.Data;
import com.example.homework.repository.AddressRepository;
import com.example.homework.repository.CompanyRepository;
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
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    public Data<CompanyDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Company> all = companyRepository.findAllByDeletedFalse(pageable);
        List<CompanyDto> collect = all.get()
                .map(Company::convert)
                .collect(Collectors.toList());
        return new Data<>(collect, all.getTotalElements());
    }

    public CompanyDto getById(Long id) {
        Optional<Company> companyOptional = companyRepository.findByIdAndDeletedFalse(id);
        if (companyOptional.isEmpty()) return null;
        return CompanyDto.builder()
                .id(companyOptional.get().getId())
                .corpName(companyOptional.get().getCorpName())
                .directorName(companyOptional.get().getDirectorName())
                .address(companyOptional.get().getAddress())
                .build();
    }

    public GenericDto create(CompanyCreateDto dto) {

        Set<ConstraintViolation<CompanyCreateDto>> violations = validator.validate(dto);
        String message = "";
        if (violations.size() > 0) {
            for (ConstraintViolation<CompanyCreateDto> violation : violations) {
                message = message.concat(violation.getMessage().concat(",\n"));
            }
            throw new RuntimeException(message);
        }

        Optional<Address> addressOptional = addressRepository.findByIdAndDeletedFalse(dto.getAddressId());
        if(addressOptional.isEmpty()) return null;

        Company save = companyRepository.save(Company.builder()
                .corpName(dto.getCorpName())
                .directorName(dto.getDirectorName())
                .address(addressOptional.get())
                .createDate(LocalDateTime.now())
                .build());

        return GenericDto.builder().id(save.getId()).build();

    }

    public CompanyDto update(Long id, CompanyCreateDto dto) throws JsonMappingException {
        Optional<Company> companyOptional = companyRepository.findByIdAndDeletedFalse(id);
        if (companyOptional.isEmpty()) return null;

        Optional<Address> addressOptional = addressRepository.findByIdAndDeletedFalse(dto.getAddressId());
        if(addressOptional.isEmpty()) return null;

        CompanyUpdateDto updateDto = CompanyUpdateDto.builder()
                .corpName(dto.getCorpName())
                .directorName(dto.getDirectorName())
                .address(addressOptional.get())
                .build();


        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Company company = objectMapper.updateValue(companyOptional.get(), updateDto);
        company.setLastModifiedDate(LocalDateTime.now());
        company = companyRepository.save(company);
        return CompanyDto.builder()
                .id(company.getId())
                .corpName(company.getCorpName())
                .directorName(company.getDirectorName())
                .address(company.getAddress())
                .build();
    }

    public boolean delete(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) return false;

        Company company = companyOptional.get();

        if (company.isDeleted()) return false;

        company.setDeleted(true);
        company.setLastModifiedDate(LocalDateTime.now());
        companyRepository.save(company);
        return true;
    }

}
