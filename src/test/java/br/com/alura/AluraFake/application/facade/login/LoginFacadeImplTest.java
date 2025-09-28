package br.com.alura.AluraFake.application.facade.login;

import br.com.alura.AluraFake.api.rest.dto.request.login.LoginDTO;
import br.com.alura.AluraFake.api.rest.dto.response.token.TokenDTO;
import br.com.alura.AluraFake.domain.service.login.LoginService;
import br.com.alura.AluraFake.dummies.LoginDummyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginFacadeImplTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginFacadeImpl loginFacade;

    @Test
    void login_should_delegate_to_loginService_and_return_token() {
        LoginDTO loginDTO = LoginDummyFactory.createLoginDto();
        TokenDTO expectedToken = LoginDummyFactory.createTokenDto();

        when(loginService.login(loginDTO)).thenReturn(expectedToken);

        TokenDTO result = loginFacade.login(loginDTO);

        assertNotNull(result);
        assertEquals(expectedToken.getToken(), result.getToken());
        verify(loginService, times(1)).login(loginDTO);
    }
}