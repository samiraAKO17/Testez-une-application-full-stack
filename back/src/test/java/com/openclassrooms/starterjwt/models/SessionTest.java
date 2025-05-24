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
        teacher = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();

        user = User.builder().id(1L).email("user@example.com").password("pass").lastName("user1").firstName("user1").build();

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

    @Test
    void testSessionDescription() {
        assertEquals("A relaxing yoga session.", session.getDescription());
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
    void testToString() {
        String result = session.toString();
        assertTrue(result.contains("Yoga Class"));
    }

}
