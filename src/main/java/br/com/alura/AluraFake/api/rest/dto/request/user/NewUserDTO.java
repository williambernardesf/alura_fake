package br.com.alura.AluraFake.api.rest.dto.request.user;

import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewUserDTO {

    @NotNull
    @Length(min = 3, max = 50)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private Role role;
    @Pattern(regexp = "^$|^.{6}$", message = "Password must be exactly 6 characters long if provided")
    private String password;
    public User toModel() {
        return new User(name, email, role);
    }
}