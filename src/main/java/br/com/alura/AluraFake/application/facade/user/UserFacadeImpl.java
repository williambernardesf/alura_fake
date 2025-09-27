package br.com.alura.AluraFake.application.facade.user;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.service.user.UserService;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private static final Logger logger = LoggerFactory.getLogger(UserFacadeImpl.class);

    private final UserService userService;

    @Override
    public UserListItemDTO newStudent(NewUserDTO newUser){
        LogUtils.info(logger, this, "newStudent", "Creating new student with email: {}", newUser.getEmail());
        UserListItemDTO response = userService.newStudent(newUser);
        LogUtils.info(logger, this, "newStudent", "Student created with ID: {}, Name: {}", response.getId(), response.getName());
        return response;
    }
}