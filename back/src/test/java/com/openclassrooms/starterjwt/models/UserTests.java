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
}
/*
class TeacherTest {

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testValidTeacher() {
        assertNotNull(teacher);
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
    }

    @Test
    void testToStringEqualsHashCode() {
        Teacher anotherTeacher = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();
        assertEquals(teacher, anotherTeacher);
        assertEquals(teacher.hashCode(), anotherTeacher.hashCode());
        assertTrue(teacher.toString().contains("Teacher(id=1"));
    }
}

class SessionTest {

    private Session session;
    private Teacher teacher;
    private User user;

    @BeforeEach
    void setUp() {
        teacher = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();

        user = User.builder().id(1L).email("user@example.com").build();

        session = Session.builder()
                .id(1L)
                .name("Yoga Class")
                .description("A relaxing yoga session.")
                .date(new Date())
                .teacher(teacher)
                .users(Arrays.asList(user))
                .build();
    }

    @Test
    void testSessionCreation() {
        assertNotNull(session);
        assertEquals("Yoga Class", session.getName());
        assertEquals("A relaxing yoga session.", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());
    }

    @Test
    void testToStringEqualsHashCode() {
        Session anotherSession = Session.builder().id(1L).name("Yoga Class").build();
        assertEquals(session, anotherSession);
        assertEquals(session.hashCode(), anotherSession.hashCode());
        assertTrue(session.toString().contains("Session(id=1"));
    }
}

* */