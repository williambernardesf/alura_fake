package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserListItemDTO toUserListItemDto(User user);

    User toUser(NewUserDTO newUserDTO);

}