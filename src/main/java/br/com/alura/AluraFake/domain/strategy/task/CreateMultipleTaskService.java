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

import static br.com.alura.AluraFake.application.ApplicationConstants.MULTIPLE_CHOICE;

@Service
@RequiredArgsConstructor
public class CreateMultipleTaskService implements TaskStrategyService{

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskOptionMapper taskOptionMapper;
    private final TaskOptionValidator taskOptionValidator;

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, Course course) {
        taskOptionValidator.validateMultipleChoice(createTaskDTO.getTaskOptions(), createTaskDTO.getStatement());
        var taskOptions = taskOptionMapper.fromGenericToListEntity(createTaskDTO.getTaskOptions());

        var task = taskMapper.fromMultipleTasktoEntity(createTaskDTO, course, taskOptions);

        taskOptions.forEach(taskOption -> taskOption.setTask(task));

        var savedTask = taskRepository.save(task);

        return taskMapper.toDto(savedTask);
    }

    @Override
    public String getName() {
        return MULTIPLE_CHOICE;
    }
}