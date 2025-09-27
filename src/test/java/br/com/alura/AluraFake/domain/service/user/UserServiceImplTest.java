package br.com.alura.AluraFake.domain.service.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.mapper.UserMapper;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void newStudent__should_create_user_when_email_not_registered() {
        NewUserDTO newUserDTO = UserDummyFactory.newUserDTO();
        User userEntity = UserDummyFactory.instructor();
        User savedUser = UserDummyFactory.instructor();
        UserListItemDTO expectedResponse = UserDummyFactory.instructorDto();

        when(userRepository.existsByEmail(newUserDTO.getEmail())).thenReturn(false);
        when(userMapper.toUser(newUserDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUser);
        when(userMapper.toUserListItemDto(savedUser)).thenReturn(expectedResponse);

        UserListItemDTO result = userService.newStudent(newUserDTO);

        assertEquals(expectedResponse.getId(), result.getId());
        assertEquals(expectedResponse.getName(), result.getName());
        assertEquals(expectedResponse.getEmail(), result.getEmail());
    }

    @Test
    void newStudent__should_throw_exception_when_email_already_registered() {
        NewUserDTO newUserDTO = UserDummyFactory.newUserDTO();
        when(userRepository.existsByEmail(newUserDTO.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.newStudent(newUserDTO));

        assertEquals("Email já cadastrado no sistema", exception.getMessage());
    }


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