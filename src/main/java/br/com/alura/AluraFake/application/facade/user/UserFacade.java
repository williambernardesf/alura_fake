package br.com.alura.AluraFake.application.facade.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;

public interface UserFacade {

    UserListItemDTO newStudent(NewUserDTO newUser);
}