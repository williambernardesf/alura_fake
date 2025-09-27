package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.application.mapper.TaskOptionMapper;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.util.LogUtils;
import br.com.alura.AluraFake.util.validation.TaskOptionValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static br.com.alura.AluraFake.application.ApplicationConstants.MULTIPLE_CHOICE;

@Service
@RequiredArgsConstructor
public class CreateMultipleTaskService implements TaskStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(CreateMultipleTaskService.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskOptionMapper taskOptionMapper;
    private final TaskOptionValidator taskOptionValidator;

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, Course course) {
        String statement = createTaskDTO.getStatement();
        LogUtils.info(logger, this, "createTask", "Validating multiple choice options for statement: {}", statement);

        taskOptionValidator.validateMultipleChoice(createTaskDTO.getTaskOptions(), statement);

        var taskOptions = taskOptionMapper.fromGenericToListEntity(createTaskDTO.getTaskOptions());
        LogUtils.info(logger, this, "createTask", "Mapping task entity for statement: {}", statement);

        var task = taskMapper.fromMultipleTasktoEntity(createTaskDTO, course, taskOptions);
        taskOptions.forEach(taskOption -> taskOption.setTask(task));

        var savedTask = taskRepository.save(task);
        LogUtils.info(logger, this, "createTask", "Task saved with statement: {}, Statement: {}", savedTask.getId(), statement);

        return taskMapper.toDto(savedTask);
    }

    @Override
    public String getName() {
        return MULTIPLE_CHOICE;
    }
}