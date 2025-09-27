package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.facade.user.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @Transactional
    @PostMapping("/user/new")
    public ResponseEntity<UserListItemDTO> newStudent(@RequestBody @Valid NewUserDTO newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.newStudent(newUser));
    }

    @GetMapping("/user/all")
    public List<UserListItemDTO> listAllUsers() {
        return null;
    }

}