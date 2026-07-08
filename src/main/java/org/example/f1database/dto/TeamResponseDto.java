package org.example.f1database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamResponseDto {

    private Long id;
    private String name;
    private String country;
    private String teamPrincipal;
    private int foundedYear;
}