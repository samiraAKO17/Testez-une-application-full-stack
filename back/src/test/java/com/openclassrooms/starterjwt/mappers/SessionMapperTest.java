package com.openclassrooms.starterjwt.mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapperImpl;
import com.openclassrooms.starterjwt.mapper.TeacherMapperImpl;
import com.openclassrooms.starterjwt.mapper.UserMapperImpl;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessionMapperTest {

    @InjectMocks
    private UserMapperImpl userMapper;

    @InjectMocks
    private TeacherMapperImpl teacherMapper;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserMapper() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("pass");
        user.setLastName("lastname");
        user.setFirstName("lastname");

        UserDto userDto = userMapper.toDto(user);
        assertNotNull(userDto);
        assertEquals("user@example.com", userDto.getEmail());

        User mappedUser = userMapper.toEntity(userDto);
        assertNotNull(mappedUser);
        assertEquals("user@example.com", mappedUser.getEmail());
    }

    @Test
    void testTeacherMapper() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");

        TeacherDto teacherDto = teacherMapper.toDto(teacher);
        assertNotNull(teacherDto);
        assertEquals("John", teacherDto.getFirstName());

        Teacher mappedTeacher = teacherMapper.toEntity(teacherDto);
        assertNotNull(mappedTeacher);
        assertEquals("John", mappedTeacher.getFirstName());
    }

    @Test
    void testSessionMapper() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(1L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user = new User();
        user.setId(1L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user);

        Session session = sessionMapper.toEntity(sessionDto);

        assertNotNull(session);
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());

        SessionDto mappedDto = sessionMapper.toDto(session);
        assertNotNull(mappedDto);
        assertEquals(1L, mappedDto.getTeacher_id());
        assertEquals(1, mappedDto.getUsers().size());
    }
}

