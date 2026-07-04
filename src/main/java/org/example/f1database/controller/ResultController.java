package org.example.f1database.controller;

import jakarta.validation.Valid;
import org.example.f1database.dto.MessageResponseDto;
import org.example.f1database.dto.ResultRequestDto;
import org.example.f1database.dto.ResultResponseDto;
import org.example.f1database.service.ResultService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    public List<ResultResponseDto> getAllResults() {
        return resultService.getAllResults();
    }

    @GetMapping("/{id}")
    public ResultResponseDto getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @PostMapping
    public ResultResponseDto createResult(@Valid @RequestBody ResultRequestDto dto) {
        return resultService.createResult(dto);
    }

    @PutMapping("/{id}")
    public ResultResponseDto updateResult(@PathVariable Long id,
                                          @Valid @RequestBody ResultRequestDto dto) {
        return resultService.updateResult(id, dto);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDto deleteResult(@PathVariable Long id) {

        resultService.deleteResult(id);

        return new MessageResponseDto("Result deleted successfully.");
    }
}