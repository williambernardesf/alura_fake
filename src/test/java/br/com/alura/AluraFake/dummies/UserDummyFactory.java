package br.com.alura.AluraFake.dummies;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Role;

import java.time.LocalDateTime;

public class UserDummyFactory {

    public static User instructor() {
        User user = new User();
        user.setId(1L);
        user.setName("Paulo");
        user.setEmail("pauloo@alura.com.br");
        user.setRole(Role.INSTRUCTOR);
        user.setPassword("dummyPassword");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static User notInstructor() {
        User user = new User();
        user.setId(2L);
        user.setName("Caio");
        user.setEmail("caio@alura.com.br");
        user.setRole(Role.STUDENT);
        user.setPassword("dummyPassword");
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public static NewUserDTO newUserDTO(){
        return NewUserDTO.builder()
                .name("Will")
                .email("will@email.com")
                .role(Role.INSTRUCTOR)
                .password("123456")
                .build();
    }

    public static UserListItemDTO instructorDto() {
        return UserListItemDTO.builder()
                .id(1L)
                .name("Paulo")
                .email("paulo@alura.com.br")
                .role(Role.INSTRUCTOR)
                .build();
    }

}