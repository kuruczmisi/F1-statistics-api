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
        TeamRequestDto requestDto = new TeamRequestDto();
        requestDto.setName("Ferrari");
        requestDto.setCountry("Italy");
        requestDto.setTeamPrincipal("Fred Vasseur");
        requestDto.setFoundedYear(1929);

        Team team = new Team();
        team.setName("Ferrari");
        team.setCountry("Italy");
        team.setTeamPrincipal("Fred Vasseur");
        team.setFoundedYear(1929);

        Team savedTeam = new Team();
        savedTeam.setId(1L);
        savedTeam.setName("Ferrari");
        savedTeam.setCountry("Italy");
        savedTeam.setTeamPrincipal("Fred Vasseur");
        savedTeam.setFoundedYear(1929);

        TeamResponseDto responseDto = new TeamResponseDto(
                1L,
                "Ferrari",
                "Italy",
                "Fred Vasseur",
                1929
        );

        when(teamMapper.toEntity(requestDto)).thenReturn(team);
        when(teamRepository.save(team)).thenReturn(savedTeam);
        when(teamMapper.toDto(savedTeam)).thenReturn(responseDto);

        TeamResponseDto result = teamService.createTeam(requestDto);

        assertEquals(1L, result.getId());
        assertEquals("Ferrari", result.getName());
        assertEquals("Italy", result.getCountry());
        assertEquals("Fred Vasseur", result.getTeamPrincipal());
        assertEquals(1929, result.getFoundedYear());
    }

    @Test
    void getTeamById_shouldReturnTeam() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Ferrari");
        team.setCountry("Italy");
        team.setTeamPrincipal("Fred Vasseur");
        team.setFoundedYear(1929);

        TeamResponseDto responseDto = new TeamResponseDto(
                1L,
                "Ferrari",
                "Italy",
                "Fred Vasseur",
                1929
        );

        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(teamMapper.toDto(team)).thenReturn(responseDto);

        TeamResponseDto result = teamService.getTeamById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Ferrari", result.getName());
        assertEquals("Fred Vasseur", result.getTeamPrincipal());
    }

    @Test
    void getTeamById_shouldThrowException() {
        when(teamRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> teamService.getTeamById(99L));
    }
}