package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAuthenticateUser() throws Exception {
        // Préparer les objets de test
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("samiraako17@gmail.com");
        loginRequest.setPassword("123456");

        UserDetailsImpl userDetails = new UserDetailsImpl(4L, "samiraako17@gmail.com", "Samira", "ABDOULKARIM", false, "123456");
        Authentication authentication = Mockito.mock(Authentication.class);

        // Mock de l'authentification
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock du token JWT
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mocked-jwt-token");

        // Mock du UserRepository
        User user = new User();
        user.setId(4L);
        user.setEmail("samiraako17@gmail.com");
        user.setFirstName("Samira");
        user.setLastName("ABDOULKARIM");
        user.setAdmin(false);

        when(userRepository.findByEmail(userDetails.getUsername())).thenReturn(java.util.Optional.of(user));

        // Exécution de la requête de test
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

}
