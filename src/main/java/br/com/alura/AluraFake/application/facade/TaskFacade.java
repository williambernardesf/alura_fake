package br.com.alura.AluraFake.application.facade;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;

public interface TaskFacade {

    TaskResponseDTO createOpenTextExercise(CreateTaskNewTextDTO createTaskNewTextDTO);

    TaskResponseDTO createSingleChoice(CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO);

    TaskResponseDTO createMultipleChoice(CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO);
}