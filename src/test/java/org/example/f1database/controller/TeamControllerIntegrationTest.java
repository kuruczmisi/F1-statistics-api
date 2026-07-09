package org.example.f1database.controller;

import org.example.f1database.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TeamControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void cleanDatabase() {
        teamRepository.deleteAll();
    }

    @Test
    void createTeam_shouldReturnCreatedTeam() throws Exception {

        String requestBody = """
                {
                    "name": "Ferrari",
                    "country": "Italy",
                    "teamPrincipal": "Fred Vasseur",
                    "foundedYear": 1929
                }
                """;

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ferrari"))
                .andExpect(jsonPath("$.country").value("Italy"))
                .andExpect(jsonPath("$.teamPrincipal").value("Fred Vasseur"))
                .andExpect(jsonPath("$.foundedYear").value(1929));
    }

    @Test
    void getAllTeams_shouldReturnTeams() throws Exception {

        String requestBody = """
                {
                    "name": "Mercedes",
                    "country": "Germany",
                    "teamPrincipal": "Toto Wolff",
                    "foundedYear": 1954
                }
                """;

        mockMvc.perform(post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(1)));
    }
}