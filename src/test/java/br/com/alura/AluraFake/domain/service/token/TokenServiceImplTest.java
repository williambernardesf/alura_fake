package br.com.alura.AluraFake.domain.service.token;

import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.exceptions.ErrorTokenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceImplTest {

    private final TokenServiceImpl tokenService = new TokenServiceImpl();

    @Test
    void generateToken_should_return_valid_jwt() {
        User user = UserDummyFactory.instructor();

        String token = tokenService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void verifyToken_should_return_subject_when_token_is_valid() {
        User user = UserDummyFactory.instructor();

        String token = tokenService.generateToken(user);

        String subject = tokenService.verifyToken(token);

        assertEquals(user.getUsername(), subject);
    }

    @Test
    void verifyToken_should_throw_exception_when_token_is_invalid() {
        String invalidToken = "invalid.token.value";

        assertThrows(ErrorTokenException.class, () -> tokenService.verifyToken(invalidToken));
    }
}