package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private SessionMapper sessionMapper;

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


    @Test
    void testGetSessionById_shouldReturnSession() throws Exception {
        Session session = new Session();
        session.setId(1L);

        when(sessionService.getById(1L)).thenReturn(session);

        mockMvc.perform(get("/api/session/1").header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSessionById_shouldReturnNotFound() throws Exception {
        when(sessionService.getById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/session/1").header("Authorization", jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateSession_shouldReturnCreatedSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test Session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("A test session description");
        sessionDto.setDate(new Date());

        Session session = new Session();
        session.setId(1L);

        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.create(any())).thenReturn(session);

        mockMvc.perform(post("/api/session").header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSession_shouldReturnOk() throws Exception {
        when(sessionService.getById(1L)).thenReturn(new Session());

        doNothing().when(sessionService).delete(1L);

        mockMvc.perform(delete("/api/session/1").header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSession_shouldReturnUpdatedSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("A test session description");
        sessionDto.setDate(new Date());

        Session session = new Session();
        session.setId(1L);

        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
        when(sessionService.update(eq(1L), any())).thenReturn(session);

        mockMvc.perform(put("/api/session/1").header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }
}
