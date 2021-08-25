package com.example.homework.payload.request;

import com.example.homework.domain.Address;
import com.example.homework.domain.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class WorkerUpdateDto {

    @NotNull(message = "name cannot be null!")
    private String name;

    @NotNull(message = "phoneNumber cannot be null!")
    private String phoneNumber;

    @NotNull(message = "address cannot be null!")
    private Address address;

    @NotNull(message = "department cannot be null!")
    private Department department;
}
