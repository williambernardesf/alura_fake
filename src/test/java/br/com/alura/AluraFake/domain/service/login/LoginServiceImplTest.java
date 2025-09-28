package br.com.alura.AluraFake.domain.service.login;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.service.token.TokenServiceImpl;
import br.com.alura.AluraFake.dummies.LoginDummyFactory;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServiceImpl tokenService;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    void login_should_authenticate_and_return_token() {
        LoginDTO loginDTO = LoginDummyFactory.createLoginDto();
        User mockUser = UserDummyFactory.instructor();

        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(tokenService.generateToken(mockUser)).thenReturn("mocked-jwt-token");

        TokenDTO result = loginService.login(loginDTO);

        assertNotNull(result);
        assertEquals("mocked-jwt-token", result.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(mockUser);
    }
}