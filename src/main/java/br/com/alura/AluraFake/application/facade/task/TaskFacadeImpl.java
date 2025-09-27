package br.com.alura.AluraFake.application.facade.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.domain.service.task.TaskService;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskFacadeImpl implements TaskFacade {

    private static final Logger logger = LoggerFactory.getLogger(TaskFacadeImpl.class);

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createOpenTextExercise(CreateTaskNewTextDTO createTaskNewTextDTO) {
        LogUtils.info(logger, this, "createOpenTextExercise", "Creating open text task with statement: {}", createTaskNewTextDTO.getStatement());
        TaskResponseDTO response = taskService.createTask(taskMapper.fromTextTaskToTaskDto(createTaskNewTextDTO));
        LogUtils.info(logger, this, "createOpenTextExercise", "Open text task created with statement: {}", response.getStatement());
        return response;
    }

    @Override
    public TaskResponseDTO createSingleChoice(CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
        LogUtils.info(logger, this, "createSingleChoice", "Creating single choice task with statement: {}", createTaskSingleChoiceDTO.getStatement());
        TaskResponseDTO response = taskService.createTask(taskMapper.fromSingleTaskToTaskDto(createTaskSingleChoiceDTO));
        LogUtils.info(logger, this, "createSingleChoice", "Single choice task created with statement: {}", response.getStatement());
        return response;
    }

    @Override
    public TaskResponseDTO createMultipleChoice(CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
        LogUtils.info(logger, this, "createMultipleChoice", "Creating multiple choice task with statement: {}", createTaskMultipleChoiceDTO.getStatement());
        TaskResponseDTO response = taskService.createTask(taskMapper.fromMultipleTextToTaskDto(createTaskMultipleChoiceDTO));
        LogUtils.info(logger, this, "createMultipleChoice", "Multiple choice task created with statement: {}", response.getStatement());
        return response;
    }
}