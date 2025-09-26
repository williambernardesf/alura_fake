package br.com.alura.AluraFake.api.rest.dto.response.user;

import br.com.alura.AluraFake.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserListItemDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Role role;
}