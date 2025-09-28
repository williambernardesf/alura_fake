package br.com.alura.AluraFake.domain.strategy.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.entity.taskoption.TaskOption;
import br.com.alura.AluraFake.domain.service.task.TaskOrderService;
import br.com.alura.AluraFake.dummies.CourseDummyFactory;
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
class CreateSingleTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskOrderService taskOrderService;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskOptionMapper taskOptionMapper;

    @Mock
    private TaskOptionValidator taskOptionValidator;

    @InjectMocks
    private CreateSingleTaskService singleTaskService;

    private CreateTaskDTO createTaskDTO;
    private Course course;
    private Task task;
    private TaskResponseDTO taskResponseDTO;

    @BeforeEach
    void setup() {
        createTaskDTO = TaskDummyFactory.createSingleChoiceTaskGeneric();
        course = CourseDummyFactory.courseWithCourseId();

        task = TaskDummyFactory.emptyTask();
        taskResponseDTO = TaskDummyFactory.taskResponseDTO();
    }

    @Test
    void createTask_should_return_taskResponseDTO() {
        List<TaskOption> taskOptionsEntity = List.of();

        doNothing().when(taskOptionValidator).validateSingleChoice(createTaskDTO.getTaskOptions(), createTaskDTO.getStatement());
        when(taskOptionMapper.fromGenericToListEntity(createTaskDTO.getTaskOptions())).thenReturn(taskOptionsEntity);
        when(taskMapper.fromSingleTaskToEntity(createTaskDTO, course, taskOptionsEntity)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = singleTaskService.createTask(createTaskDTO, course);

        assertEquals(taskResponseDTO, result);

        verify(taskOrderService).validateAndShiftTasks(course.getId(), createTaskDTO.getOrder());
        verify(taskOptionValidator).validateSingleChoice(createTaskDTO.getTaskOptions(), createTaskDTO.getStatement());
        verify(taskOptionMapper).fromGenericToListEntity(createTaskDTO.getTaskOptions());
        verify(taskMapper).fromSingleTaskToEntity(createTaskDTO, course, taskOptionsEntity);
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(task);
    }

    @Test
    void getName_should_return_single_choice_constant() {
        assertEquals("SINGLE_CHOICE", singleTaskService.getName());
    }
}