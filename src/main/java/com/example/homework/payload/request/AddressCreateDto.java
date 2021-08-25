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
public class AddressCreateDto {

    @NotNull(message = "street cannot be null!")
    private String street;

    @NotNull(message = "homeNumber cannot be null! ")
    private Integer homeNumber;
}
