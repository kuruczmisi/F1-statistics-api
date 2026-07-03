package org.example.f1database.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultResponseDto {

    private Long id;

    private int position;
    private int points;

    private Long driverId;
    private String driverName;

    private Long raceId;
    private String raceName;
}