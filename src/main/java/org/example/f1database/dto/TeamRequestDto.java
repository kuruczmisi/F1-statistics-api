package org.example.f1database.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestDto {

    @NotBlank(message = "Team name is required")
    private String name;

    @NotBlank(message = "Country is required")
    private String country;
}