package org.example.f1database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RaceResponseDto {

    private Long id;
    private String name;
    private String location;
    private int year;
}