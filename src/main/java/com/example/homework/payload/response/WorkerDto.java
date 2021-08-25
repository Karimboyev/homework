package com.example.homework.payload.response;

import com.example.homework.domain.Address;
import com.example.homework.domain.Company;
import com.example.homework.domain.Department;
import com.example.homework.payload.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class WorkerDto extends GenericDto {

    private String name;

    private String phoneNumber;

    private Address address;

    private Department department;
}
