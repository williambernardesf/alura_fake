package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.application.mapper.TaskOptionMapper;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.util.validation.TaskOptionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.alura.AluraFake.application.ApplicationConstants.SINGLE_CHOICE;

@Service
@RequiredArgsConstructor
public class CreateSingleTaskService implements TaskStrategyService{

    private final TaskRepository taskRepository;
    private final TaskOptionValidator taskOptionValidator;
    private final TaskMapper taskMapper;
    private final TaskOptionMapper taskOptionMapper;

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, Course course) {
        taskOptionValidator.validateSingleChoice(createTaskDTO.getTaskOptions(), createTaskDTO.getStatement());

        var taskOptions = taskOptionMapper.fromGenericToListEntity(createTaskDTO.getTaskOptions());

        var task = taskMapper.fromSingleTaskToEntity(createTaskDTO, course, taskOptions);

        taskOptions.forEach(taskOption -> taskOption.setTask(task));

        var savedTask = taskRepository.save(task);

        return taskMapper.toDto(savedTask);
    }

    @Override
    public String getName() {
        return SINGLE_CHOICE;
    }
}