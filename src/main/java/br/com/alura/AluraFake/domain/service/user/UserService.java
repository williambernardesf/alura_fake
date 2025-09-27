package br.com.alura.AluraFake.domain.service.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;

public interface UserService {
    UserListItemDTO newStudent(NewUserDTO newUser);

    UserListItemDTO getUserById(Long userId);
}