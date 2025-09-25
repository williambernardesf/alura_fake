package br.com.alura.AluraFake.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/task/new/opentext")
    public ResponseEntity newOpenTextExercise(@Valid @RequestBody CreateTaskNewTextDTO createTaskNewTextDTO) {
        return taskService.createOpenTextExercise(createTaskNewTextDTO);
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity newSingleChoice(@Valid @RequestBody CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
        return taskService.createSingleChoice(createTaskSingleChoiceDTO);
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity newMultipleChoice(@Valid @RequestBody CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
        return taskService.createMultipleChoice(createTaskMultipleChoiceDTO);
    }

}