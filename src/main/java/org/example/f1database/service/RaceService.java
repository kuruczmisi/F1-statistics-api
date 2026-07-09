package org.example.f1database.service;

import org.example.f1database.dto.RaceRequestDto;
import org.example.f1database.dto.RaceResponseDto;
import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Race;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.mapper.RaceMapper;
import org.example.f1database.repository.DriverRepository;
import org.example.f1database.repository.RaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RaceService {

    private final RaceRepository raceRepository;
    private final DriverRepository driverRepository;
    private final RaceMapper raceMapper;

    public RaceService(RaceRepository raceRepository,
                       DriverRepository driverRepository,
                       RaceMapper raceMapper) {
        this.raceRepository = raceRepository;
        this.driverRepository = driverRepository;
        this.raceMapper = raceMapper;
    }

    public List<RaceResponseDto> getAllRaces() {
        return raceRepository.findAll()
                .stream()
                .map(raceMapper::toDto)
                .toList();
    }

    public RaceResponseDto getRaceById(Long id) {
        Race race = raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race not found with id: " + id));

        return raceMapper.toDto(race);
    }

    public List<RaceResponseDto> getRacesByYear(int year) {
        return raceRepository.findByYear(year)
                .stream()
                .map(raceMapper::toDto)
                .toList();
    }

    public RaceResponseDto createRace(RaceRequestDto dto) {
        List<Driver> drivers = getDriversFromIds(dto.getDriverIds());

        Race race = raceMapper.toEntity(dto, drivers);
        Race savedRace = raceRepository.save(race);

        return raceMapper.toDto(savedRace);
    }

    public RaceResponseDto updateRace(Long id, RaceRequestDto dto) {
        Race existingRace = raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race not found with id: " + id));

        List<Driver> drivers = getDriversFromIds(dto.getDriverIds());

        existingRace.setName(dto.getName());
        existingRace.setLocation(dto.getLocation());
        existingRace.setYear(dto.getYear());
        existingRace.setDrivers(drivers);

        Race updatedRace = raceRepository.save(existingRace);

        return raceMapper.toDto(updatedRace);
    }

    public void deleteRace(Long id) {
        Race existingRace = raceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Race not found with id: " + id));

        raceRepository.delete(existingRace);
    }

    private List<Driver> getDriversFromIds(List<Long> driverIds) {
        if (driverIds == null || driverIds.isEmpty()) {
            return new ArrayList<>();
        }

        return driverIds.stream()
                .map(driverId -> driverRepository.findById(driverId)
                        .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + driverId)))
                .toList();
    }
}