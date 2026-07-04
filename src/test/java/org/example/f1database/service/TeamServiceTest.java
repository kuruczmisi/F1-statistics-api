package org.example.f1database.service;

import org.example.f1database.dto.TeamRequestDto;
import org.example.f1database.dto.TeamResponseDto;
import org.example.f1database.entity.Team;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.mapper.TeamMapper;
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
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    @Test
    void createTeam_shouldReturnSavedTeam() {
        // Arrange
        TeamRequestDto requestDto = new TeamRequestDto();
        requestDto.setName("Ferrari");
        requestDto.setCountry("Italy");

        Team team = new Team();
        team.setName("Ferrari");
        team.setCountry("Italy");

        Team savedTeam = new Team();
        savedTeam.setId(1L);
        savedTeam.setName("Ferrari");
        savedTeam.setCountry("Italy");

        TeamResponseDto responseDto = new TeamResponseDto(1L, "Ferrari", "Italy");

        when(teamMapper.toEntity(requestDto)).thenReturn(team);
        when(teamRepository.save(team)).thenReturn(savedTeam);
        when(teamMapper.toDto(savedTeam)).thenReturn(responseDto);

        // Act
        TeamResponseDto result = teamService.createTeam(requestDto);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Ferrari", result.getName());
        assertEquals("Italy", result.getCountry());
    }

    @Test
    void getTeamById_shouldReturnTeam() {
        // Arrange
        Team team = new Team();
        team.setId(1L);
        team.setName("Ferrari");
        team.setCountry("Italy");

        TeamResponseDto responseDto = new TeamResponseDto(1L, "Ferrari", "Italy");

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamMapper.toDto(team)).thenReturn(responseDto);

        // Act
        TeamResponseDto result = teamService.getTeamById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Ferrari", result.getName());
    }

    @Test
    void getTeamById_shouldThrowException() {
        // Arrange
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> teamService.getTeamById(99L));
    }
}