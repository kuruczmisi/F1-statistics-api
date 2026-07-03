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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final DriverRepository driverRepository;
    private final RaceRepository raceRepository;
    private final ResultMapper resultMapper;

    public ResultService(ResultRepository resultRepository,
                         DriverRepository driverRepository,
                         RaceRepository raceRepository,
                         ResultMapper resultMapper) {
        this.resultRepository = resultRepository;
        this.driverRepository = driverRepository;
        this.raceRepository = raceRepository;
        this.resultMapper = resultMapper;
    }

    public List<ResultResponseDto> getAllResults() {
        return resultRepository.findAll()
                .stream()
                .map(resultMapper::toDto)
                .toList();
    }

    public ResultResponseDto getResultById(Long id) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Result not found with id: " + id));

        return resultMapper.toDto(result);
    }

    public ResultResponseDto createResult(ResultRequestDto dto) {
        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + dto.getDriverId()));

        Race race = raceRepository.findById(dto.getRaceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Race not found with id: " + dto.getRaceId()));

        Result result = resultMapper.toEntity(dto, driver, race);
        Result savedResult = resultRepository.save(result);

        return resultMapper.toDto(savedResult);
    }

    public ResultResponseDto updateResult(Long id, ResultRequestDto dto) {
        Result existingResult = resultRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Result not found with id: " + id));

        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + dto.getDriverId()));

        Race race = raceRepository.findById(dto.getRaceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Race not found with id: " + dto.getRaceId()));

        existingResult.setPosition(dto.getPosition());
        existingResult.setPoints(dto.getPoints());
        existingResult.setDriver(driver);
        existingResult.setRace(race);

        Result updatedResult = resultRepository.save(existingResult);

        return resultMapper.toDto(updatedResult);
    }

    public void deleteResult(Long id) {
        Result existingResult = resultRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Result not found with id: " + id));

        resultRepository.delete(existingResult);
    }
}