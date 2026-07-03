package org.example.f1database.service;

import org.example.f1database.entity.Driver;
import org.example.f1database.exception.ResourceNotFoundException;
import org.example.f1database.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + id));
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Long id, Driver updatedDriver) {

        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + id));

        existingDriver.setName(updatedDriver.getName());
        existingDriver.setNationality(updatedDriver.getNationality());
        existingDriver.setNumber(updatedDriver.getNumber());
        existingDriver.setTeam(updatedDriver.getTeam());

        return driverRepository.save(existingDriver);
    }

    public void deleteDriver(Long id) {

        Driver existingDriver = driverRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found with id: " + id));

        driverRepository.delete(existingDriver);
    }
}