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
public class DepartmentCreateDto {

    @NotNull(message = "name cannot be null!")
    private String name;


    @NotNull(message = "companyId cannot be null! ")
    private Long companyId;
}
