package br.com.alura.AluraFake.domain.service.token;

import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.exceptions.ErrorTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService{

    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345678");
            return JWT.create()
                    .withIssuer("Alura Fake")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expiration(30))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new ErrorTokenException("Error while generating token");
        }
    }

    public String verifyToken(String token){
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345678");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("Alura Fake")
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception){
            throw new ErrorTokenException("Error while validating token");
        }
    }

    private Instant expiration(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }
}