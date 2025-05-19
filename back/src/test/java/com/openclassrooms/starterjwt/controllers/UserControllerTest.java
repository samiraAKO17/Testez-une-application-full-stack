package com.openclassrooms.starterjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

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
    void testFindById_Success() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        UserDto userDto = new UserDto();
        userDto.setEmail("user@example.com");

        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mockMvc.perform(get("/api/user/1").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete_Success() throws Exception {
        User user = new User();
        user.setId(3L);
        user.setEmail("samiraako17@gmail.com");

        when(userService.findById(3L)).thenReturn(user);

        mockMvc.perform(delete("/api/user/3").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(3L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(delete("/api/user/1").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete_InvalidId() throws Exception {
        mockMvc.perform(delete("/api/user/invalid").header(HttpHeaders.AUTHORIZATION, jwtToken))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_Unauthorized() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }
}
