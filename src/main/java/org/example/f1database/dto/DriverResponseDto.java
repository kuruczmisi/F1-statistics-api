package org.example.f1database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DriverResponseDto {

    private Long id;
    private String name;
    private String nationality;
    private int number;
    private Long teamId;
    private String teamName;
}