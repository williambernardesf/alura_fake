package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.facade.task.TaskFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskFacade taskFacade;

    @PostMapping("/task/new/opentext")
    public ResponseEntity<TaskResponseDTO> newOpenTextExercise(@Valid @RequestBody CreateTaskNewTextDTO createTaskNewTextDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskFacade.createOpenTextExercise(createTaskNewTextDTO));
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<TaskResponseDTO> newSingleChoice(@Valid @RequestBody CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskFacade.createSingleChoice(createTaskSingleChoiceDTO));
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity<TaskResponseDTO> newMultipleChoice(@Valid @RequestBody CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskFacade.createMultipleChoice(createTaskMultipleChoiceDTO));
    }

}