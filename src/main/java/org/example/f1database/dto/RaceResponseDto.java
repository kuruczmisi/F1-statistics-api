package org.example.f1database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RaceResponseDto {

    private Long id;
    private String name;
    private String location;
    private int year;
    private List<Long> driverIds;
    private List<String> driverNames;
}