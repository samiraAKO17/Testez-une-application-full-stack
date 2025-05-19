package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void findAll_shouldReturnListOfTeachers() {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        List<Teacher> result = teacherService.findAll();

        assertEquals(2, result.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnTeacher_whenFound() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher result = teacherService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_shouldReturnNull_whenNotFound() {
        when(teacherRepository.findById(999L)).thenReturn(Optional.empty());

        Teacher result = teacherService.findById(999L);

        assertNull(result);
    }
}

