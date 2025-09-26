package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.application.mapper.TaskOptionMapper;
import br.com.alura.AluraFake.util.validation.TaskOptionValidator;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateMultipleTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskOptionMapper taskOptionMapper;

    @Mock
    private TaskOptionValidator taskOptionValidator;

    @InjectMocks
    private CreateMultipleTaskService multipleTaskService;

    private CreateTaskDTO createTaskDTO;
    private Course course;
    private Task task;
    private TaskResponseDTO taskResponseDTO;

    @BeforeEach
    void setup() {
        createTaskDTO = TaskDummyFactory.createMultipleChoiceTaskGeneric();
        course = new Course();
        course.setId(1L);

        task = new Task();
        taskResponseDTO = TaskDummyFactory.taskResponseDTO();
    }

    @Test
    void createTask_should_return_taskResponseDTO() {
        List taskOptionsEntity = List.of();

        doNothing().when(taskOptionValidator).validateMultipleChoice(createTaskDTO.getTaskOptions(), createTaskDTO.getStatement());
        when(taskOptionMapper.fromGenericToListEntity(createTaskDTO.getTaskOptions())).thenReturn(taskOptionsEntity);
        when(taskMapper.fromMultipleTasktoEntity(createTaskDTO, course, taskOptionsEntity)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = multipleTaskService.createTask(createTaskDTO, course);

        assertEquals(taskResponseDTO, result);
        verify(taskOptionValidator).validateMultipleChoice(createTaskDTO.getTaskOptions(), createTaskDTO.getStatement());
        verify(taskOptionMapper).fromGenericToListEntity(createTaskDTO.getTaskOptions());
        verify(taskMapper).fromMultipleTasktoEntity(createTaskDTO, course, taskOptionsEntity);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(task);
    }

    @Test
    void getName_should_return_multiple_choice_constant() {
        assertEquals("MULTIPLE_CHOICE", multipleTaskService.getName());
    }
}