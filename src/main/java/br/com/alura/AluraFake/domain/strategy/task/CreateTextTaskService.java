package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.alura.AluraFake.application.ApplicationConstants.OPEN_TEXT;

@Service
@RequiredArgsConstructor
public class CreateTextTaskService implements TaskStrategyService{

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, Course course) {
        var task = taskMapper.toEntityWithNoOptions(createTaskDTO, course, null);

        var savedTask = taskRepository.save(task);

        return taskMapper.toDto(savedTask);
    }

    @Override
    public String getName() {
        return OPEN_TEXT;
    }
}