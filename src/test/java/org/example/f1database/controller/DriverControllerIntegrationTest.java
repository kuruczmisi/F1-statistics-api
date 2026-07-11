package org.example.f1database.controller;

import org.example.f1database.entity.Driver;
import org.example.f1database.entity.Team;
import org.example.f1database.repository.DriverRepository;
import org.example.f1database.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DriverControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Team savedTeam;

    @BeforeEach
    void setUp() {
        driverRepository.deleteAll();
        teamRepository.deleteAll();

        Team team = new Team();
        team.setName("Ferrari");
        team.setCountry("Italy");
        team.setTeamPrincipal("Fred Vasseur");
        team.setFoundedYear(1929);

        savedTeam = teamRepository.save(team);
    }

    @Test
    void createDriver_shouldReturnCreatedDriver() throws Exception {

        String requestBody = """
                {
                    "name": "Lewis Hamilton",
                    "nationality": "British",
                    "number": 44,
                    "teamId": %d
                }
                """.formatted(savedTeam.getId());

        mockMvc.perform(post("/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lewis Hamilton"))
                .andExpect(jsonPath("$.nationality").value("British"))
                .andExpect(jsonPath("$.number").value(44))
                .andExpect(jsonPath("$.teamId").value(savedTeam.getId()))
                .andExpect(jsonPath("$.teamName").value("Ferrari"));
    }

    @Test
    void getDriverById_shouldReturnDriver() throws Exception {

        Driver driver = new Driver();
        driver.setName("Charles Leclerc");
        driver.setNationality("Monégasque");
        driver.setNumber(16);
        driver.setTeam(savedTeam);

        Driver savedDriver = driverRepository.save(driver);

        mockMvc.perform(get("/drivers/{id}", savedDriver.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedDriver.getId()))
                .andExpect(jsonPath("$.name").value("Charles Leclerc"))
                .andExpect(jsonPath("$.teamName").value("Ferrari"));
    }

    @Test
    void updateDriver_shouldReturnUpdatedDriver() throws Exception {

        Driver driver = new Driver();
        driver.setName("Lewis Hamilton");
        driver.setNationality("British");
        driver.setNumber(44);
        driver.setTeam(savedTeam);

        Driver savedDriver = driverRepository.save(driver);

        String requestBody = """
                {
                    "name": "Lewis Hamilton",
                    "nationality": "British",
                    "number": 1,
                    "teamId": %d
                }
                """.formatted(savedTeam.getId());

        mockMvc.perform(put("/drivers/{id}", savedDriver.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedDriver.getId()))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.teamName").value("Ferrari"));
    }

    @Test
    void deleteDriver_shouldReturnSuccessMessage() throws Exception {

        Driver driver = new Driver();
        driver.setName("Carlos Sainz");
        driver.setNationality("Spanish");
        driver.setNumber(55);
        driver.setTeam(savedTeam);

        Driver savedDriver = driverRepository.save(driver);

        mockMvc.perform(delete("/drivers/{id}", savedDriver.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Driver deleted successfully."));
    }

    @Test
    void getDriverById_shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/drivers/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error")
                        .value("Driver not found with id: 999"));
    }
}