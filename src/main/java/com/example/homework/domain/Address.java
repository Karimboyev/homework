package com.example.homework.domain;

import com.example.homework.payload.response.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
public class Address extends AbstractDomain{

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer homeNumber;


    public static AddressDto convert(Address address) {
        return AddressDto.builder()
                .id(address.id)
                .street(address.street)
                .homeNumber(address.homeNumber)
                .build();
    }
}
