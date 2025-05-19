package com.openclassrooms.starterjwt.mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.mapper.TeacherMapperImpl;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    @BeforeEach
    void setUp() {
        teacherMapper = new TeacherMapperImpl();
    }

    @Test
    void testTeacherMapper_ToDtoAndToEntity() {
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
    void testTeacherMapper_NullInput() {
        assertNull(teacherMapper.toDto((Teacher) null));
        assertNull(teacherMapper.toEntity((TeacherDto) null));
    }

    @Test
    void testTeacherMapper_ToDtoList_NonEmpty() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");

        List<TeacherDto> teacherDtos = teacherMapper.toDto(Arrays.asList(teacher));
        assertNotNull(teacherDtos);
        assertEquals(1, teacherDtos.size());
        assertEquals("John", teacherDtos.get(0).getFirstName());
    }

    @Test
    void testTeacherMapper_ToDtoList_Empty() {
        List<TeacherDto> teacherDtos = teacherMapper.toDto(Collections.emptyList());
        assertNotNull(teacherDtos);
        assertTrue(teacherDtos.isEmpty());
    }

    @Test
    void testTeacherMapper_ToEntityList_NonEmpty() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("John");

        List<Teacher> teachers = teacherMapper.toEntity(Arrays.asList(teacherDto));
        assertNotNull(teachers);
        assertEquals(1, teachers.size());
        assertEquals("John", teachers.get(0).getFirstName());
    }

    @Test
    void testTeacherMapper_ToEntityList_Empty() {
        List<Teacher> teachers = teacherMapper.toEntity(Collections.emptyList());
        assertNotNull(teachers);
        assertTrue(teachers.isEmpty());
    }
}
