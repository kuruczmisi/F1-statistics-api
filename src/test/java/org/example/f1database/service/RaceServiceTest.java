package org.example.f1database.service;

import org.example.f1database.dto.RaceRequestDto;
import org.example.f1database.dto.RaceResponseDto;
import org.example.f1database.entity.Race;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.mapper.RaceMapper;
import org.example.f1database.repository.RaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RaceServiceTest {

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private RaceMapper raceMapper;

    @InjectMocks
    private RaceService raceService;

    @Test
    void createRace_shouldReturnSavedRace() {
        // Arrange
        RaceRequestDto requestDto = new RaceRequestDto();
        requestDto.setName("Hungarian Grand Prix");
        requestDto.setLocation("Hungary");
        requestDto.setYear(2025);

        Race race = new Race();
        race.setName("Hungarian Grand Prix");
        race.setLocation("Hungary");
        race.setYear(2025);

        Race savedRace = new Race();
        savedRace.setId(1L);
        savedRace.setName("Hungarian Grand Prix");
        savedRace.setLocation("Hungary");
        savedRace.setYear(2025);

        RaceResponseDto responseDto = new RaceResponseDto(
                1L,
                "Hungarian Grand Prix",
                "Hungary",
                2025
        );

        when(raceMapper.toEntity(requestDto)).thenReturn(race);
        when(raceRepository.save(race)).thenReturn(savedRace);
        when(raceMapper.toDto(savedRace)).thenReturn(responseDto);

        // Act
        RaceResponseDto result = raceService.createRace(requestDto);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Hungarian Grand Prix", result.getName());
        assertEquals(2025, result.getYear());
    }

    @Test
    void getRaceById_shouldReturnRace() {
        // Arrange
        Race race = new Race();
        race.setId(1L);
        race.setName("Hungarian Grand Prix");
        race.setLocation("Hungary");
        race.setYear(2025);

        RaceResponseDto responseDto = new RaceResponseDto(
                1L,
                "Hungarian Grand Prix",
                "Hungary",
                2025
        );

        when(raceRepository.findById(1L)).thenReturn(Optional.of(race));
        when(raceMapper.toDto(race)).thenReturn(responseDto);

        // Act
        RaceResponseDto result = raceService.getRaceById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Hungarian Grand Prix", result.getName());
    }

    @Test
    void getRaceById_shouldThrowException() {
        // Arrange
        when(raceRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> raceService.getRaceById(99L));
    }
}