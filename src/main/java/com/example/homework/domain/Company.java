package com.example.homework.domain;

import com.example.homework.payload.response.CompanyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Company extends AbstractDomain{

    @Column(nullable = false)
    private String corpName;

    @Column(nullable = false)
    private String directorName;

    @OneToOne
    private Address address;

    public static CompanyDto convert(Company company) {
        return CompanyDto.builder()
                .id(company.id)
                .corpName(company.corpName)
                .directorName(company.directorName)
                .address(company.address)
                .build();
    }
}
