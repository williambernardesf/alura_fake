package br.com.alura.AluraFake.domain.service.token;

import br.com.alura.AluraFake.domain.entity.user.User;

public interface TokenService {

    String generateToken(User user);

    String verifyToken(String token);
}