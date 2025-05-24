package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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
    void testGettersAndSetters() {
        assertEquals(1L, teacher.getId());
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
    }

    @Test
    void testEqualsAndHashCode() {
        Teacher anotherTeacher = new Teacher();
        anotherTeacher.setId(1L);

        assertEquals(teacher, anotherTeacher);
        assertEquals(teacher.hashCode(), anotherTeacher.hashCode());
    }

    @Test
    void testToString() {
        String expected = "Teacher(id=1, lastName=Doe, firstName=John, createdAt=" + teacher.getCreatedAt() + ", updatedAt=" + teacher.getUpdatedAt() + ")";
        assertEquals(expected, teacher.toString());
    }

    @Test
    void testCreatedAtAndUpdatedAt() {
        assertNotNull(teacher.getCreatedAt());
        assertNotNull(teacher.getUpdatedAt());
        assertTrue(teacher.getUpdatedAt().isAfter(teacher.getCreatedAt()));
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
    @Test
    void testTeacherBuilder() {
        Teacher teacher = Teacher.builder()
                .id(2L)
                .firstName("Alice")
                .lastName("Smith")
                .build();

        assertEquals("Alice", teacher.getFirstName());
    }
}
