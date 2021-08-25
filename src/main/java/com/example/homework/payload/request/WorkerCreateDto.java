package com.example.homework.payload.request;

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
public class WorkerCreateDto {

    @NotNull(message = "name cannot be null!")
    private String name;

    @NotNull(message = "phoneNumber cannot be null!")
    private String phoneNumber;

    @NotNull(message = "addressId cannot be null!")
    private Long addressId;

    @NotNull(message = "departmentId cannot be null!")
    private Long departmentId;
}
