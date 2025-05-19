package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    void create_shouldSaveAndReturnSession() {
        Session session = new Session();
        session.setName("Test");

        when(sessionRepository.save(session)).thenReturn(session);

        Session result = sessionService.create(session);

        assertEquals("Test", result.getName());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void getById_shouldReturnSession_whenExists() {
        Session session = new Session();
        session.setId(1L);

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Session result = sessionService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void participate_shouldAddUserToSession_whenValid() {
        User user = new User();
        user.setId(2L);

        Session session = new Session();
        session.setId(3L);
        session.setUsers(new ArrayList<>());

        when(sessionRepository.findById(3L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        sessionService.participate(3L, 2L);

        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void participate_shouldThrowBadRequest_ifAlreadyParticipates() {
        User user = new User();
        user.setId(2L);

        Session session = new Session();
        session.setId(3L);
        session.setUsers(new ArrayList<>(Arrays.asList(user)));

        when(sessionRepository.findById(3L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.participate(3L, 2L));
    }

    @Test
    void participate_shouldThrowNotFound_ifSessionOrUserMissing() {
        when(sessionRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(3L, 2L));
    }
}
