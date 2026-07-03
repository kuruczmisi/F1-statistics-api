package org.example.f1database.mapper;

import org.example.f1database.dto.DriverRequestDto;
import org.example.f1database.dto.DriverResponseDto;
import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class DriverMapper {

    public Driver toEntity(DriverRequestDto dto, Team team) {

        Driver driver = new Driver();

        driver.setName(dto.getName());
        driver.setNationality(dto.getNationality());
        driver.setNumber(dto.getNumber());
        driver.setTeam(team);

        return driver;
    }

    public DriverResponseDto toDto(Driver driver) {

        return new DriverResponseDto(
                driver.getId(),
                driver.getName(),
                driver.getNationality(),
                driver.getNumber(),
                driver.getTeam() != null ? driver.getTeam().getId() : null,
                driver.getTeam() != null ? driver.getTeam().getName() : null
        );
    }
}