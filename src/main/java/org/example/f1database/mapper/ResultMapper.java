package org.example.f1database.mapper;

import org.example.f1database.dto.ResultRequestDto;
import org.example.f1database.dto.ResultResponseDto;
import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Race;
import org.example.f1database.entity.Result;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    public Result toEntity(ResultRequestDto dto, Driver driver, Race race) {

        Result result = new Result();

        result.setPosition(dto.getPosition());
        result.setPoints(dto.getPoints());
        result.setDriver(driver);
        result.setRace(race);

        return result;
    }

    public ResultResponseDto toDto(Result result) {

        return new ResultResponseDto(
                result.getId(),
                result.getPosition(),
                result.getPoints(),
                result.getDriver() != null ? result.getDriver().getId() : null,
                result.getDriver() != null ? result.getDriver().getName() : null,
                result.getRace() != null ? result.getRace().getId() : null,
                result.getRace() != null ? result.getRace().getName() : null
        );
    }
}