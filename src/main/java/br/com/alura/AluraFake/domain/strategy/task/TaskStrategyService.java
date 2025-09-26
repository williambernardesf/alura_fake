package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;

public interface TaskStrategyService {

    TaskResponseDTO createTask(CreateTaskDTO createTaskDTO, Course course);

    String getName();
}