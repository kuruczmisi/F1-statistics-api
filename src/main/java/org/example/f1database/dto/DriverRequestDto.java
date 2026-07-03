package org.example.f1database.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequestDto {

    @NotBlank(message = "Driver name is required")
    private String name;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @Positive(message = "Driver number must be positive")
    private int number;

    @NotNull(message = "Team id is required")
    private Long teamId;
}