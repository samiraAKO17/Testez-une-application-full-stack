package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {

    @Test
    void testSignupRequestFields() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("John", request.getFirstName());
        assertEquals("Doe", request.getLastName());
        assertEquals("password123", request.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        SignupRequest r1 = new SignupRequest();
        r1.setEmail("test@example.com");
        r1.setFirstName("John");
        r1.setLastName("Doe");
        r1.setPassword("password123");

        SignupRequest r2 = new SignupRequest();
        r2.setEmail("test@example.com");
        r2.setFirstName("John");
        r2.setLastName("Doe");
        r2.setPassword("password123");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToStringIsNotNull() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password123");

        assertNotNull(request.toString());
    }


}

