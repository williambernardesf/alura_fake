package br.com.alura.AluraFake.domain.service.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;

public interface TaskService {
    TaskResponseDTO createTask(CreateTaskDTO createTaskDTO);
}