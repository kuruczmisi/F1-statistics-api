package org.example.f1database.service;

import org.example.f1database.entity.Result;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.repository.ResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Result getResultById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Result not found with id: " + id));
    }

    public Result createResult(Result result) {
        return resultRepository.save(result);
    }

    public Result updateResult(Long id, Result updatedResult) {

        Result existingResult = resultRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Result not found with id: " + id));

        existingResult.setPosition(updatedResult.getPosition());
        existingResult.setPoints(updatedResult.getPoints());
        existingResult.setDriver(updatedResult.getDriver());
        existingResult.setRace(updatedResult.getRace());

        return resultRepository.save(existingResult);
    }

    public void deleteResult(Long id) {

        Result existingResult = resultRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Result not found with id: " + id));

        resultRepository.delete(existingResult);
    }
}