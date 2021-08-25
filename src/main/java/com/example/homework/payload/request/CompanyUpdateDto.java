package com.example.homework.payload.request;

import com.example.homework.domain.Address;
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
public class CompanyUpdateDto {

    @NotNull(message = "corpName cannot be null!")
    private String corpName;

    @NotNull(message = "directorName cannot be null! ")
    private String directorName;

    @NotNull(message = "addressId cannot be null! ")
    private Address address;
}
