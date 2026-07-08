package org.example.f1database.dto;

import jakarta.validation.constraints.Min;
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

    @NotBlank(message = "Team principal is required")
    private String teamPrincipal;

    @Min(value = 1900, message = "Founded year must be valid")
    private int foundedYear;
}