package br.com.alura.AluraFake.domain.service.user;

import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.mapper.UserMapper;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserById_should_return_userListItemDTO_if_user_is_instructor() {
        User instructor = UserDummyFactory.instructor();
        UserListItemDTO dto = UserDummyFactory.instructorDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(userMapper.toUserListItemDto(instructor)).thenReturn(dto);

        UserListItemDTO result = userService.getUserById(1L);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getEmail(), result.getEmail());
    }

    @Test
    void getUserById_should_throw_exception_if_user_not_found() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.getUserById(99L));

        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());
    }

    @Test
    void getUserById_should_throw_exception_if_user_not_instructor() {
        User student = UserDummyFactory.notInstructor();
        when(userRepository.findById(1L)).thenReturn(Optional.of(student));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.getUserById(1L));

        assertEquals("400 BAD_REQUEST \"User is not a instructor\"", exception.getMessage());
    }
}