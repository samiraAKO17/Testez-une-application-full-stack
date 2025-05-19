package com.openclassrooms.starterjwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;

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
    void findAll_shouldReturnListOfTeachers() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        when(teacherService.findAll()).thenReturn( new ArrayList<>(Arrays.asList(teacher)));
        when(teacherMapper.toDto( new ArrayList<>(Arrays.asList(teacher)))).thenReturn(Arrays.asList(new TeacherDto(1L, "John", "Doe", LocalDateTime.now (),  LocalDateTime.now())));

        mockMvc.perform(get("/api/teacher").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstName").value("Doe"));
    }

    @Test
    void findById_shouldReturnTeacher_whenExists() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(new com.openclassrooms.starterjwt.dto.TeacherDto(1L, "Doe", "John", LocalDateTime.now(),LocalDateTime.now()));

        mockMvc.perform(get("/api/teacher/1").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void findById_shouldReturnNotFound_whenTeacherMissing() throws Exception {
        when(teacherService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/teacher/99").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void findById_shouldReturnBadRequest_whenInvalidId() throws Exception {
        mockMvc.perform(get("/api/teacher/abc").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isBadRequest());
    }
}

