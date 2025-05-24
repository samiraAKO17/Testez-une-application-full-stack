package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    @Test
    void testJwtResponseFields() {
        JwtResponse response = new JwtResponse(
                "token123", 1L, "user@example.com", "John", "Doe", true
        );

        assertEquals("token123", response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(1L, response.getId());
        assertEquals("user@example.com", response.getUsername());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertTrue(response.getAdmin());
    }

    @Test
    void testSetters() {
        JwtResponse response = new JwtResponse(
                "token123", 1L, "user@example.com", "John", "Doe", true
        );

        // Utilisation des setters
        response.setId(2L);
        response.setUsername("newuser@example.com");
        response.setFirstName("Jane");
        response.setLastName("Smith");
        response.setAdmin(false);

        // Vérification des valeurs modifiées
        assertEquals(2L, response.getId());
        assertEquals("newuser@example.com", response.getUsername());
        assertEquals("Jane", response.getFirstName());
        assertEquals("Smith", response.getLastName());
        assertFalse(response.getAdmin());
    }
}

