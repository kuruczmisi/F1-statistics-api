package org.example.f1database.controller;

import org.example.f1database.dto.DriverRequestDto;
import org.example.f1database.dto.DriverResponseDto;
import org.example.f1database.service.DriverService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public List<DriverResponseDto> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public DriverResponseDto getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PostMapping
    public DriverResponseDto createDriver(@RequestBody DriverRequestDto dto) {
        return driverService.createDriver(dto);
    }

    @PutMapping("/{id}")
    public DriverResponseDto updateDriver(@PathVariable Long id,
                                          @RequestBody DriverRequestDto dto) {
        return driverService.updateDriver(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }
}