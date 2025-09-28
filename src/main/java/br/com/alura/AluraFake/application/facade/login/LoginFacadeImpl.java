package br.com.alura.AluraFake.application.facade.login;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import br.com.alura.AluraFake.domain.service.login.LoginService;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginFacadeImpl implements LoginFacade {

    private final LoginService loginService;

    private static final Logger logger = LoggerFactory.getLogger(LoginFacadeImpl.class);


    @Override
    public TokenDTO login(LoginDTO loginDto) {
        LogUtils.info(logger, this, "login", "Authenticating user {}", loginDto.getEmail());
        return loginService.login(loginDto);
    }
}