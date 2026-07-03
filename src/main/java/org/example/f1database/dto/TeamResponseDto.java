package org.example.f1database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamResponseDto {

    private Long id;
    private String name;
    private String country;
}