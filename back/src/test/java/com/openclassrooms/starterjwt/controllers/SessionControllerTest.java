package com.openclassrooms.starterjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("samiraako17@gmail.com");
        loginRequest.setPassword("123456");

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andReturn().getResponse().getContentAsString();

        JwtResponse jwtResponse = objectMapper.readValue(response, JwtResponse.class);
        jwtToken = "Bearer " + jwtResponse.getToken();
    }

    @Test
    void testParticipateInSession() throws Exception {
        Long sessionId = 2L;
        Long userId = 3L;

        doNothing().when(sessionService).participate(sessionId, userId);

        mockMvc.perform(post("/api/session/" + sessionId + "/participate/" + userId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }


    @Test
    void testNoLongerParticipateInSession() throws Exception {
        Long sessionId = 2L;
        Long userId = 3L;

        doNothing().when(sessionService).noLongerParticipate(sessionId, userId);

        mockMvc.perform(delete("/api/session/" + sessionId + "/participate/" + userId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSessionById() throws Exception {
        Long sessionId = 2L;
        when(sessionService.getById(sessionId)).thenReturn(new Session());

        mockMvc.perform(get("/api/session/" + sessionId)
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllSessions() throws Exception {
        mockMvc.perform(get("/api/session")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }
}
