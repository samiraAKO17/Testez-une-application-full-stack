package com.openclassrooms.starterjwt.security;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
    }

    @Test
    void testUserDetailsImpl_Getters() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@example.com", "firstname", "lastname", false, "password");

        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testUserDetailsImpl_EqualsAndHashCode() {
        UserDetailsImpl userDetails1 = new UserDetailsImpl(1L, "test@example.com", "firstname", "lastname", false, "password");
        UserDetailsImpl userDetails2 = new UserDetailsImpl(1L, "test@example.com", "firstname", "lastname", false, "password");

        assertEquals(userDetails1, userDetails2);
        assertEquals(userDetails1.getId(), userDetails2.getId());
    }

    @Test
    void testUserDetailsImpl_ToString() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test@example.com", "firstname", "lastname", false, "password");
        assertNotNull(userDetails.toString());
    }
}
