package br.com.alura.AluraFake.api.rest.dto.request.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {

    @NotBlank
    @Email
    private String email;
    private String password;
}