package com.example.homework.domain;


import com.example.homework.payload.response.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Department extends AbstractDomain{

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Company company;


    public static DepartmentDto convert(Department department) {
        return DepartmentDto.builder()
                .id(department.id)
                .name(department.name)
                .company(department.company)
                .build();
    }
}
