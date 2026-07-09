package org.example.f1database.mapper;

import org.example.f1database.dto.RaceRequestDto;
import org.example.f1database.dto.RaceResponseDto;
import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Race;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RaceMapper {

    public Race toEntity(RaceRequestDto dto, List<Driver> drivers) {
        Race race = new Race();

        race.setName(dto.getName());
        race.setLocation(dto.getLocation());
        race.setYear(dto.getYear());
        race.setDrivers(drivers);

        return race;
    }

    public RaceResponseDto toDto(Race race) {
        return new RaceResponseDto(
                race.getId(),
                race.getName(),
                race.getLocation(),
                race.getYear(),
                race.getDrivers()
                        .stream()
                        .map(Driver::getId)
                        .toList(),
                race.getDrivers()
                        .stream()
                        .map(Driver::getName)
                        .toList()
        );
    }
}