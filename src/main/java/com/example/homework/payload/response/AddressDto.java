package com.example.homework.payload.response;

import com.example.homework.payload.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AddressDto extends GenericDto {

    private String street;

    private Integer homeNumber;

}
