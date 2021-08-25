package com.example.homework.payload.response;

import com.example.homework.payload.GenericDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Data<T extends GenericDto> {
    List<T> data;

    long totalCount;
}
