package org.example.f1database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaceRequestDto {

    private String name;
    private String location;
    private int year;
}