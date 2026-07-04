package org.example.f1database.service;

import org.example.f1database.dto.DriverRequestDto;
import org.example.f1database.dto.DriverResponseDto;
import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Team;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.mapper.DriverMapper;
import org.example.f1database.repository.DriverRepository;
import org.example.f1database.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private DriverMapper driverMapper;

    @InjectMocks
    private DriverService driverService;

    @Test
    void createDriver_shouldReturnSavedDriver() {

        // Arrange
        Team team = new Team();
        team.setId(1L);
        team.setName("Ferrari");
        team.setCountry("Italy");

        DriverRequestDto requestDto = new DriverRequestDto();
        requestDto.setName("Lewis Hamilton");
        requestDto.setNationality("British");
        requestDto.setNumber(44);
        requestDto.setTeamId(1L);

        Driver driver = new Driver();
        driver.setName("Lewis Hamilton");
        driver.setNationality("British");
        driver.setNumber(44);
        driver.setTeam(team);

        Driver savedDriver = new Driver();
        savedDriver.setId(1L);
        savedDriver.setName("Lewis Hamilton");
        savedDriver.setNationality("British");
        savedDriver.setNumber(44);
        savedDriver.setTeam(team);

        DriverResponseDto responseDto = new DriverResponseDto(
                1L,
                "Lewis Hamilton",
                "British",
                44,
                1L,
                "Ferrari"
        );

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(driverMapper.toEntity(requestDto, team)).thenReturn(driver);
        when(driverRepository.save(driver)).thenReturn(savedDriver);
        when(driverMapper.toDto(savedDriver)).thenReturn(responseDto);

        // Act
        DriverResponseDto result = driverService.createDriver(requestDto);

        // Assert
        assertEquals("Lewis Hamilton", result.getName());
        assertEquals("British", result.getNationality());
        assertEquals(44, result.getNumber());
        assertEquals("Ferrari", result.getTeamName());
    }

    @Test
    void getDriverById_shouldReturnDriver() {

        // Arrange
        Team team = new Team();
        team.setId(1L);
        team.setName("Ferrari");

        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("Lewis Hamilton");
        driver.setNationality("British");
        driver.setNumber(44);
        driver.setTeam(team);

        DriverResponseDto responseDto = new DriverResponseDto(
                1L,
                "Lewis Hamilton",
                "British",
                44,
                1L,
                "Ferrari"
        );

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(driverMapper.toDto(driver)).thenReturn(responseDto);

        // Act
        DriverResponseDto result = driverService.getDriverById(1L);

        // Assert
        assertEquals("Lewis Hamilton", result.getName());
        assertEquals("Ferrari", result.getTeamName());
    }

    @Test
    void getDriverById_shouldThrowException() {

        // Arrange
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> driverService.getDriverById(99L));
    }
}