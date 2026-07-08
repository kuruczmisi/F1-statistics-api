package org.example.f1database.mapper;

import org.example.f1database.dto.TeamRequestDto;
import org.example.f1database.dto.TeamResponseDto;
import org.example.f1database.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team toEntity(TeamRequestDto dto) {
        Team team = new Team();

        team.setName(dto.getName());
        team.setCountry(dto.getCountry());
        team.setTeamPrincipal(dto.getTeamPrincipal());
        team.setFoundedYear(dto.getFoundedYear());

        return team;
    }

    public TeamResponseDto toDto(Team team) {
        return new TeamResponseDto(
                team.getId(),
                team.getName(),
                team.getCountry(),
                team.getTeamPrincipal(),
                team.getFoundedYear()
        );
    }
}