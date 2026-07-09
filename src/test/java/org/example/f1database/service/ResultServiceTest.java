package org.example.f1database.service;

import org.example.f1database.dto.ResultRequestDto;
import org.example.f1database.dto.ResultResponseDto;
import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Race;
import org.example.f1database.entity.Result;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.mapper.ResultMapper;
import org.example.f1database.repository.DriverRepository;
import org.example.f1database.repository.RaceRepository;
import org.example.f1database.repository.ResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultServiceTest {

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private ResultMapper resultMapper;

    @InjectMocks
    private ResultService resultService;

    @Test
    void createResult_shouldReturnSavedResult() {

        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Lewis Hamilton");

        Race race = new Race();
        race.setId(1L);
        race.setName("Hungarian GP");

        ResultRequestDto requestDto = new ResultRequestDto();
        requestDto.setPosition(1);
        requestDto.setPoints(25);
        requestDto.setDriverId(1L);
        requestDto.setRaceId(1L);

        Result result = new Result();
        result.setPosition(1);
        result.setPoints(25);
        result.setDriver(driver);
        result.setRace(race);

        Result savedResult = new Result();
        savedResult.setId(1L);
        savedResult.setPosition(1);
        savedResult.setPoints(25);
        savedResult.setDriver(driver);
        savedResult.setRace(race);

        ResultResponseDto responseDto = new ResultResponseDto(
                1L,
                1,
                25,
                1L,
                "Lewis Hamilton",
                1L,
                "Hungarian GP"
        );

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
        when(resultMapper.toEntity(requestDto, driver, race)).thenReturn(result);
        when(resultRepository.save(result)).thenReturn(savedResult);
        when(resultMapper.toDto(savedResult)).thenReturn(responseDto);

        ResultResponseDto response = resultService.createResult(requestDto);

        assertEquals(1, response.getPosition());
        assertEquals(25, response.getPoints());
        assertEquals("Lewis Hamilton", response.getDriverName());
    }

    @Test
    void getResultById_shouldThrowException() {

        when(resultRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> resultService.getResultById(100L));
    }
}