package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.facade.task.TaskFacade;
import br.com.alura.AluraFake.util.LogUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskFacade taskFacade;

    @PostMapping("/task/new/opentext")
    public ResponseEntity<TaskResponseDTO> newOpenTextExercise(@Valid @RequestBody CreateTaskNewTextDTO createTaskNewTextDTO) {
        LogUtils.info(logger, this, "newOpenTextExercise", "Creating open text task with statement: {}", createTaskNewTextDTO.getStatement());
        TaskResponseDTO response = taskFacade.createOpenTextExercise(createTaskNewTextDTO);
        LogUtils.info(logger, this, "newOpenTextExercise", "Open text task created with statement: {}", response.getStatement());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<TaskResponseDTO> newSingleChoice(@Valid @RequestBody CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
        LogUtils.info(logger, this, "newSingleChoice", "Creating single choice task with statement: {}", createTaskSingleChoiceDTO.getStatement());
        TaskResponseDTO response = taskFacade.createSingleChoice(createTaskSingleChoiceDTO);
        LogUtils.info(logger, this, "newSingleChoice", "Single choice task created with statement: {}", response.getStatement());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity<TaskResponseDTO> newMultipleChoice(@Valid @RequestBody CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
        LogUtils.info(logger, this, "newMultipleChoice", "Creating multiple choice task with statement: {}", createTaskMultipleChoiceDTO.getStatement());
        TaskResponseDTO response = taskFacade.createMultipleChoice(createTaskMultipleChoiceDTO);
        LogUtils.info(logger, this, "newMultipleChoice", "Multiple choice task created with statement: {}", response.getStatement());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}