package br.com.alura.AluraFake.dummies;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import br.com.alura.AluraFake.domain.entity.task.Task;

public class LoginDummyFactory {

    public static Task emptyTask(){
        return new Task();
    }

    public static LoginDTO createLoginDto() {
        return LoginDTO.builder()
                .email("will@gmail.com")
                .password("123456")
                .build();
    }

    public static TokenDTO createTokenDto() {
        return TokenDTO.builder()
                .token("token")
                .build();
    }
}