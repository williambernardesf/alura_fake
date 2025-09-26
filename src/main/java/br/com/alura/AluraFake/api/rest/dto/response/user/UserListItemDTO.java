package br.com.alura.AluraFake.api.rest.dto.response.user;

import br.com.alura.AluraFake.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class UserListItemDTO implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Role role;
}