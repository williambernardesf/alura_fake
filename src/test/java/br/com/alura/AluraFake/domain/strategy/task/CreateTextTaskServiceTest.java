package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.application.mapper.TaskMapper;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTextTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private CreateTextTaskService textTaskService;

    private CreateTaskDTO createTaskDTO;
    private Course course;
    private Task task;
    private TaskResponseDTO taskResponseDTO;

    @BeforeEach
    void setup() {
        createTaskDTO = TaskDummyFactory.createOpenTextTaskGeneric();
        course = new Course();
        course.setId(1L);

        task = new Task();
        taskResponseDTO = TaskDummyFactory.taskResponseDTO();
    }

    @Test
    void createTask_should_return_taskResponseDTO() {
        when(taskMapper.toEntityWithNoOptions(createTaskDTO, course, null)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = textTaskService.createTask(createTaskDTO, course);

        assertEquals(taskResponseDTO, result);

        verify(taskMapper).toEntityWithNoOptions(createTaskDTO, course, null);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(task);
    }

    @Test
    void getName_should_return_open_text_constant() {
        assertEquals("OPEN_TEXT", textTaskService.getName());
    }
}