package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private User user;
    private Validator validator;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("user@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password")
                .admin(false)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void testUserModel_Constructor() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("user@example.com");
        user1.setPassword("password");

        assertEquals(1L, user1.getId());
        assertEquals("user@example.com", user1.getEmail());
        assertEquals("password", user1.getPassword());
    }

    @Test
    void testValidUser() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmail() {
        user.setEmail("invalid-email");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testToStringEqualsHashCode() {
        User anotherUser = User.builder().id(1L).email("user@example.com").password("pass").lastName("user1").firstName("user1").build();
        assertEquals(user, anotherUser);
        assertEquals(user.hashCode(), anotherUser.hashCode());
        assertTrue(user.toString().contains("User(id=1"));
    }

    @Test
    void testUserModel_EqualsAndHashCode() {
        User user1 = new User(1L, "user@example.com", "lastname", "firstname", "password", false, LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User(1L, "user@example.com", "lastname", "firstname", "password", false, LocalDateTime.now(), LocalDateTime.now());

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
    @Test
    void testUserBuilderToString() {
        User.UserBuilder builder = User.builder();
        String str = builder.toString();
        assertNotNull(str);  // Juste vérifier que ça ne crash pas et renvoie bien une chaîne
    }

    @Test
    void testUserBuilderSetters() {
        User.UserBuilder builder = User.builder();

        builder.id(10L)
                .email("builder@example.com")
                .lastName("BuilderLastName")
                .firstName("BuilderFirstName")
                .password("builderPass")
                .admin(true)
                .createdAt(LocalDateTime.now().minusDays(2))
                .updatedAt(LocalDateTime.now());

        User builtUser = builder.build();

        assertEquals(10L, builtUser.getId());
        assertEquals("builder@example.com", builtUser.getEmail());
        assertEquals("BuilderLastName", builtUser.getLastName());
        assertEquals("BuilderFirstName", builtUser.getFirstName());
        assertEquals("builderPass", builtUser.getPassword());
        assertTrue(builtUser.isAdmin());
    }

}