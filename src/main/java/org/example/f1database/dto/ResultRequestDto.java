package org.example.f1database.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRequestDto {

    private int position;
    private int points;

    private Long driverId;
    private Long raceId;
}