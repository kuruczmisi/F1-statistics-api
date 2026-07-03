package org.example.f1database.mapper;

import org.example.f1database.dto.RaceRequestDto;
import org.example.f1database.dto.RaceResponseDto;
import org.example.f1database.entity.Race;
import org.springframework.stereotype.Component;

@Component
public class RaceMapper {

    public Race toEntity(RaceRequestDto dto) {
        Race race = new Race();

        race.setName(dto.getName());
        race.setLocation(dto.getLocation());
        race.setYear(dto.getYear());

        return race;
    }

    public RaceResponseDto toDto(Race race) {
        return new RaceResponseDto(
                race.getId(),
                race.getName(),
                race.getLocation(),
                race.getYear()
        );
    }
}