package br.com.alura.AluraFake.domain.service.login;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginService {

    TokenDTO login(@Valid @RequestBody LoginDTO loginDto);
}