package org.example.f1database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequestDto {

    private String name;
    private String nationality;
    private int number;
    private Long teamId;
}