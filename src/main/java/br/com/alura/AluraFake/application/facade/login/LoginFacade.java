package br.com.alura.AluraFake.application.facade.login;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginFacade {

    TokenDTO login(@Valid @RequestBody LoginDTO loginDto);

}