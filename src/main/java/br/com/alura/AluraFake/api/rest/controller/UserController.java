package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.user.NewUserDTO;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.application.facade.user.UserFacade;
import br.com.alura.AluraFake.util.LogUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserFacade userFacade;

    @Transactional
    @PostMapping("/user/new")
    public ResponseEntity<UserListItemDTO> newStudent(@RequestBody @Valid NewUserDTO newUser) {
        LogUtils.info(logger, this, "newStudent", "Creating new student with email: {}", newUser.getEmail());
        UserListItemDTO response = userFacade.newStudent(newUser);
        LogUtils.info(logger, this, "newStudent", "Student created with ID: {}, Name: {}", response.getId(), response.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/user/all")
    public List<UserListItemDTO> listAllUsers() {
        return null;
    }
}