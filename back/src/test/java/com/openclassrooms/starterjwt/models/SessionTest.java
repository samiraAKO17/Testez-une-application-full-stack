package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    private Session session;
    private User user;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        session = new Session();
        session.setId(1L);
        session.setName("Yoga Class");
        session.setDescription("A relaxing yoga session.");
        session.setDate(new Date());
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user));
    }

    @Test
    void testSessionCreation() {
        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals("Yoga Class", session.getName());
        assertEquals("A relaxing yoga session.", session.getDescription());
        assertNotNull(session.getDate());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());
    }

    @Test
    void testAddUserToSession() {
        User newUser = new User();
        newUser.setId(2L);
        newUser.setEmail("anotheruser@example.com");

        List<User> users = new ArrayList<>(session.getUsers());
        users.add(newUser);

        session.setUsers(users);

        assertEquals(2, session.getUsers().size());
        assertTrue(session.getUsers().contains(newUser));
    }

    @Test
    void testSessionTeacher() {
        assertEquals(1L, session.getTeacher().getId());
        assertEquals("John", session.getTeacher().getFirstName());
        assertEquals("Doe", session.getTeacher().getLastName());
    }

    @Test
    void testSessionName() {
        assertEquals("Yoga Class", session.getName());
    }

    @Test
    void testSessionDescription() {
        assertEquals("A relaxing yoga session.", session.getDescription());
    }
}
