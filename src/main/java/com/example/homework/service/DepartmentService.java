package com.example.homework.service;

import com.example.homework.domain.Company;
import com.example.homework.domain.Department;
import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.DepartmentCreateDto;
import com.example.homework.payload.request.DepartmentUpdateDto;
import com.example.homework.payload.response.Data;
import com.example.homework.payload.response.DepartmentDto;
import com.example.homework.repository.CompanyRepository;
import com.example.homework.repository.DepartmentRepository;
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
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final CompanyRepository companyRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    public Data<DepartmentDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Department> all = departmentRepository.findAllByDeletedFalse(pageable);
        List<DepartmentDto> collect = all.get()
                .map(Department::convert)
                .collect(Collectors.toList());
        return new Data<>(collect, all.getTotalElements());
    }

    public DepartmentDto getById(Long id) {
        Optional<Department> departmentOptional = departmentRepository.findByIdAndDeletedFalse(id);
        if (departmentOptional.isEmpty()) return null;
        return DepartmentDto.builder()
                .id(departmentOptional.get().getId())
                .name(departmentOptional.get().getName())
                .company(departmentOptional.get().getCompany())
                .build();
    }

    public GenericDto create(DepartmentCreateDto dto) {

        Set<ConstraintViolation<DepartmentCreateDto>> violations = validator.validate(dto);
        String message = "";
        if (violations.size() > 0) {
            for (ConstraintViolation<DepartmentCreateDto> violation : violations) {
                message = message.concat(violation.getMessage().concat(",\n"));
            }
            throw new RuntimeException(message);
        }

        Optional<Company> companyOptional = companyRepository.findByIdAndDeletedFalse(dto.getCompanyId());
        if (companyOptional.isEmpty()) return null;

        Department save = departmentRepository.save(Department.builder()
                .name(dto.getName())
                .company(companyOptional.get())
                .createDate(LocalDateTime.now())
                .build());

        return GenericDto.builder().id(save.getId()).build();

    }

    public DepartmentDto update(Long id, DepartmentCreateDto dto) throws JsonMappingException {
        Optional<Department> DepartmentOptional = departmentRepository.findByIdAndDeletedFalse(id);
        if (DepartmentOptional.isEmpty()) return null;

        Optional<Company> companyOptional = companyRepository.findByIdAndDeletedFalse(dto.getCompanyId());
        if (companyOptional.isEmpty()) return null;

        DepartmentUpdateDto updateDto = DepartmentUpdateDto.builder()
                .name(dto.getName())
                .company(companyOptional.get())
                .build();


        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Department department = objectMapper.updateValue(DepartmentOptional.get(), updateDto);
        department.setLastModifiedDate(LocalDateTime.now());
        department = departmentRepository.save(department);
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .company(department.getCompany())
                .build();
    }

    public boolean delete(Long id) {

        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isEmpty()) return false;

        Department department = departmentOptional.get();

        if (department.isDeleted()) return false;

        department.setDeleted(true);
        department.setLastModifiedDate(LocalDateTime.now());
        departmentRepository.save(department);
        return true;
    }

}
