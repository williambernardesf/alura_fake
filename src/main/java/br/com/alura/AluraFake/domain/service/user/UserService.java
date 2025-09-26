package br.com.alura.AluraFake.domain.service.user;

import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;

public interface UserService {

    UserListItemDTO getUserById(Long userId);
}