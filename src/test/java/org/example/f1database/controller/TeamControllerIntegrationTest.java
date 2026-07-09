package org.example.f1database.controller;

import org.example.f1database.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.test.context.ActiveProfiles;
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

    @Test
    void createTeam_shouldReturnCreatedTeam() throws Exception {

        // Arrange
        String requestBody = """
                {
                    "name": "Ferrari",
                    "country": "Italy"
                }
                """;

        // Act + Assert
        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ferrari"))
                .andExpect(jsonPath("$.country").value("Italy"));
    }

    @Test
    void getAllTeams_shouldReturnTeams() throws Exception {

        // Arrange
        if (teamRepository.count() == 0) {
            String requestBody = """
                    {
                        "name": "Mercedes",
                        "country": "Germany"
                    }
                    """;

            mockMvc.perform(post("/teams")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody));
        }

        // Act + Assert
        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(1)));
    }
}