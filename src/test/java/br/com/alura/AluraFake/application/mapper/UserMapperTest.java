package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserToUserListItemDTO_usingDummy() {
        User user = UserDummyFactory.instructor();

        UserListItemDTO dto = mapper.toUserListItemDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getRole(), dto.getRole());
    }

    @Test
    void shouldMapNewUserDTOToUser_usingDummy() {
        NewUserDTO dto = UserDummyFactory.newUserDTO();

        User user = mapper.toUser(dto);

        assertNull(user.getId());
        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getRole(), user.getRole());
        assertEquals(dto.getPassword(), user.getPassword());
    }
}