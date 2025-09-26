package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping("/user/new")
    public ResponseEntity newStudent(@RequestBody @Valid NewUserDTO newUser) {
        if(userRepository.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("email", "Email já cadastrado no sistema"));
        }
        User user = newUser.toModel();
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/user/all")
    public List<UserListItemDTO> listAllUsers() {
        return null;
    }

}