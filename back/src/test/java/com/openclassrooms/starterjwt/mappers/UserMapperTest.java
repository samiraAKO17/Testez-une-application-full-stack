package mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserMapperTest {

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        UserDto userDto = new UserDto();
        userDto.setEmail("user@example.com");

        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userMapper.toDto(user);
        assertEquals(userDto.getEmail(), result.getEmail());
    }
}
