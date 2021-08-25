package com.example.homework.domain;

import com.example.homework.payload.response.WorkerDto;
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
public class Worker extends AbstractDomain{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne
    private Address address;

    @ManyToOne
    private Department department;


    public static WorkerDto convert(Worker worker) {
        return WorkerDto.builder()
                .id(worker.id)
                .name(worker.name)
                .phoneNumber(worker.phoneNumber)
                .address(worker.address)
                .department(worker.department)
                .build();
    }
}
