package com.openclassrooms.starterjwt.mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.mapper.UserMapperImpl;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserMapperTest {

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void testUserMapper_ToDtoAndToEntity() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("pass");
        user.setLastName("lastname");
        user.setFirstName("firstname");

        UserDto userDto = userMapper.toDto(user);
        assertNotNull(userDto);
        assertEquals("user@example.com", userDto.getEmail());
        assertEquals("firstname", userDto.getFirstName());

        User mappedUser = userMapper.toEntity(userDto);
        assertNotNull(mappedUser);
        assertEquals("user@example.com", mappedUser.getEmail());
        assertEquals("firstname", mappedUser.getFirstName());
    }

    @Test
    void testUserMapper_NullInput() {
        assertNull(((UserMapper) userMapper).toDto((User) null));
        assertNull(((UserMapper) userMapper).toEntity((UserDto) null));
    }

    @Test
    void testUserMapper_ToDtoList_NonEmpty() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setLastName("lastname");
        user.setFirstName("firstname");
        user.setPassword("pass");

        List<UserDto> userDtos = userMapper.toDto(Arrays.asList(user));
        assertNotNull(userDtos);
        assertEquals(1, userDtos.size());
        assertEquals("user@example.com", userDtos.get(0).getEmail());
    }

    @Test
    void testUserMapper_ToDtoList_Empty() {
        List<UserDto> userDtos = userMapper.toDto(Collections.emptyList());
        assertNotNull(userDtos);
        assertTrue(userDtos.isEmpty());
    }

    @Test
    void testUserMapper_ToEntityList_NonEmpty() {
        UserDto userDto = new UserDto();
        userDto.setEmail("user@example.com");
        userDto.setLastName("lastname");
        userDto.setFirstName("firstname");
        userDto.setPassword("pass");

        List<User> users = userMapper.toEntity(Arrays.asList(userDto));
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("user@example.com", users.get(0).getEmail());
    }

    @Test
    void testUserMapper_ToEntityList_Empty() {
        List<User> users = userMapper.toEntity(Collections.emptyList());
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }
}
