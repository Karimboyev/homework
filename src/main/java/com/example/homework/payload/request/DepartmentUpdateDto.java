package com.example.homework.payload.request;

import com.example.homework.domain.Company;
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
public class DepartmentUpdateDto {

    @NotNull(message = "name cannot be null!")
    private String name;


    @NotNull(message = "company cannot be null! ")
    private Company company;
}
