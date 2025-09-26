package br.com.alura.AluraFake.application.facade.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.domain.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskFacadeImpl implements TaskFacade{

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createOpenTextExercise(CreateTaskNewTextDTO createTaskNewTextDTO) {
        return taskService.createTask(taskMapper.fromTextTaskToTaskDto(createTaskNewTextDTO));
    }

    @Override
    public TaskResponseDTO createSingleChoice(CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
        return taskService.createTask(taskMapper.fromSingleTaskToTaskDto(createTaskSingleChoiceDTO));

    }

    @Override
    public TaskResponseDTO createMultipleChoice(CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
        return taskService.createTask(taskMapper.fromMultipleTextToTaskDto(createTaskMultipleChoiceDTO));

    }
}