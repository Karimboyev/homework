package com.example.homework.payload.response;

import com.example.homework.domain.Address;
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
public class CompanyDto extends GenericDto {

    private String corpName;

    private String directorName;

    private Address address;
}
