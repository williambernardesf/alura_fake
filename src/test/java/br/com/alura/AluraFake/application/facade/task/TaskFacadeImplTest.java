package br.com.alura.AluraFake.application.facade.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.domain.service.task.TaskService;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskFacadeImplTest {

    private TaskService taskService;
    private TaskMapper taskMapper;
    private TaskFacadeImpl taskFacade;

    @BeforeEach
    void setup() {
        taskService = mock(TaskService.class);
        taskMapper = mock(TaskMapper.class);
        taskFacade = new TaskFacadeImpl(taskService, taskMapper);
    }

    @Test
    void createOpenTextExercise_should_delegate_to_service() {
        CreateTaskNewTextDTO dto = TaskDummyFactory.createOpenTextTask();
        TaskResponseDTO response = TaskDummyFactory.taskResponseDTO();

        CreateTaskDTO taskDTO = TaskDummyFactory.createOpenTextTaskGeneric();

        when(taskMapper.fromTextTaskToTaskDto(dto)).thenReturn(taskDTO);
        when(taskService.createTask(taskDTO)).thenReturn(response);

        TaskResponseDTO result = taskFacade.createOpenTextExercise(dto);

        assertEquals(response, result);
        verify(taskMapper, times(1)).fromTextTaskToTaskDto(dto);
        verify(taskService, times(1)).createTask(taskDTO);
    }

    @Test
    void createSingleChoice_should_delegate_to_service() {
        CreateTaskSingleChoiceDTO dto = TaskDummyFactory.createSingleChoiceTask();
        TaskResponseDTO response = TaskDummyFactory.taskResponseDTO();

        CreateTaskDTO taskDTO = TaskDummyFactory.createSingleChoiceTaskGeneric();

        when(taskMapper.fromSingleTaskToTaskDto(dto)).thenReturn(taskDTO);
        when(taskService.createTask(taskDTO)).thenReturn(response);

        TaskResponseDTO result = taskFacade.createSingleChoice(dto);

        assertEquals(response, result);
        verify(taskMapper, times(1)).fromSingleTaskToTaskDto(dto);
        verify(taskService, times(1)).createTask(taskDTO);
    }

    @Test
    void createMultipleChoice_should_delegate_to_service() {
        CreateTaskMultipleChoiceDTO dto = TaskDummyFactory.createMultipleChoiceTask();
        TaskResponseDTO response = TaskDummyFactory.taskResponseDTO();
        CreateTaskDTO taskDTO = TaskDummyFactory.createMultipleChoiceTaskGeneric();

        when(taskMapper.fromMultipleTextToTaskDto(dto)).thenReturn(taskDTO);
        when(taskService.createTask(taskDTO)).thenReturn(response);

        TaskResponseDTO result = taskFacade.createMultipleChoice(dto);

        assertEquals(response, result);
        verify(taskMapper, times(1)).fromMultipleTextToTaskDto(dto);
        verify(taskService, times(1)).createTask(taskDTO);
    }
}