package com.example.homework.service;

import com.example.homework.domain.Address;
import com.example.homework.domain.Department;
import com.example.homework.domain.Worker;
import com.example.homework.payload.GenericDto;
import com.example.homework.payload.request.WorkerCreateDto;
import com.example.homework.payload.request.WorkerUpdateDto;
import com.example.homework.payload.response.Data;
import com.example.homework.payload.response.WorkerDto;
import com.example.homework.repository.AddressRepository;
import com.example.homework.repository.DepartmentRepository;
import com.example.homework.repository.WorkerRepository;
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
public class WorkerService {

    private final WorkerRepository workerRepository;

    private final AddressRepository addressRepository;

    private final DepartmentRepository departmentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public WorkerService(WorkerRepository workerRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository) {
        this.workerRepository = workerRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
    }

    public Data<WorkerDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Worker> all = workerRepository.findAllByDeletedFalse(pageable);
        List<WorkerDto> collect = all.get()
                .map(Worker::convert)
                .collect(Collectors.toList());
        return new Data<>(collect, all.getTotalElements());
    }

    public WorkerDto getById(Long id) {
        Optional<Worker> workerOptional = workerRepository.findByIdAndDeletedFalse(id);
        if (workerOptional.isEmpty()) return null;
        return WorkerDto.builder()
                .id(workerOptional.get().getId())
                .name(workerOptional.get().getName())
                .phoneNumber(workerOptional.get().getPhoneNumber())
                .address(workerOptional.get().getAddress())
                .department(workerOptional.get().getDepartment())
                .build();
    }

    public GenericDto create(WorkerCreateDto dto) {

        Set<ConstraintViolation<WorkerCreateDto>> violations = validator.validate(dto);
        String message = "";
        if (violations.size() > 0) {
            for (ConstraintViolation<WorkerCreateDto> violation : violations) {
                message = message.concat(violation.getMessage().concat(",\n"));
            }
            throw new RuntimeException(message);
        }

        Optional<Address> addressOptional = addressRepository.findByIdAndDeletedFalse(dto.getAddressId());
        if(addressOptional.isEmpty()) return null;

        Optional<Department> departmentOptional = departmentRepository.findByIdAndDeletedFalse(dto.getDepartmentId());
        if (departmentOptional.isEmpty()) return null;

        Worker save = workerRepository.save(Worker.builder()
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .address(addressOptional.get())
                .department(departmentOptional.get())
                .createDate(LocalDateTime.now())
                .build());

        return GenericDto.builder().id(save.getId()).build();

    }

    public WorkerDto update(Long id, WorkerCreateDto dto) throws JsonMappingException {
        Optional<Worker> WorkerOptional = workerRepository.findByIdAndDeletedFalse(id);
        if (WorkerOptional.isEmpty()) return null;

        Optional<Address> addressOptional = addressRepository.findByIdAndDeletedFalse(dto.getAddressId());
        if(addressOptional.isEmpty()) return null;

        Optional<Department> departmentOptional = departmentRepository.findByIdAndDeletedFalse(dto.getDepartmentId());
        if(departmentOptional.isEmpty()) return null;

        WorkerUpdateDto updateDto = WorkerUpdateDto.builder()
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .address(addressOptional.get())
                .department(departmentOptional.get())
                .build();


        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Worker worker = objectMapper.updateValue(WorkerOptional.get(), updateDto);
        worker.setLastModifiedDate(LocalDateTime.now());
        worker = workerRepository.save(worker);
        return WorkerDto.builder()
                .id(worker.getId())
                .name(worker.getName())
                .phoneNumber(worker.getPhoneNumber())
                .address(worker.getAddress())
                .department(worker.getDepartment())
                .build();
    }

    public boolean delete(Long id) {
        Optional<Worker> WorkerOptional = workerRepository.findById(id);
        if (WorkerOptional.isEmpty()) return false;

        Worker Worker = WorkerOptional.get();

        if (Worker.isDeleted()) return false;

        Worker.setDeleted(true);
        Worker.setLastModifiedDate(LocalDateTime.now());
        workerRepository.save(Worker);
        return true;
    }

}
