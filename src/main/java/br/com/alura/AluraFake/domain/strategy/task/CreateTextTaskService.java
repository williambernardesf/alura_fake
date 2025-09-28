package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.service.task.TaskOrderService;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static br.com.alura.AluraFake.application.ApplicationConstants.OPEN_TEXT;

@Service
@RequiredArgsConstructor
public class CreateTextTaskService implements TaskStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(CreateTextTaskService.class);

    private final TaskOrderService taskOrderService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, Course course) {
        String statement = createTaskDTO.getStatement();
        LogUtils.info(logger, this, "createTask", "Creating open text task for statement: {}", statement);

        var task = taskMapper.toEntityWithNoOptions(createTaskDTO, course, null);
        LogUtils.info(logger, this, "createTask", "Mapped task entity for statement: {}", statement);

        taskOrderService.validateAndShiftTasks(course.getId(), createTaskDTO.getOrder());

        var savedTask = taskRepository.save(task);
        LogUtils.info(logger, this, "createTask", "Task saved with ID: {}, Statement: {}", savedTask.getId(), statement);

        return taskMapper.toDto(savedTask);
    }

    @Override
    public String getName() {
        return OPEN_TEXT;
    }
}