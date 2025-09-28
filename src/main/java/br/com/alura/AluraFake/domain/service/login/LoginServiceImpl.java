package br.com.alura.AluraFake.domain.service.login;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import br.com.alura.AluraFake.application.facade.login.LoginFacadeImpl;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.service.token.TokenServiceImpl;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginFacadeImpl.class);

    private final AuthenticationManager authenticationManager;
    private final TokenServiceImpl tokenService;

    @Override
    public TokenDTO login(LoginDTO loginDto) {
        LogUtils.info(logger, this, "login", "Authenticating user {}", loginDto.getEmail());
        var autenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        var authentication = authenticationManager.authenticate(autenticationToken);

        String token = tokenService.generateToken((User) authentication.getPrincipal());
        LogUtils.info(logger, this, "login", "Generated token for: ", loginDto.getEmail());

        return new TokenDTO(token);
    }
}