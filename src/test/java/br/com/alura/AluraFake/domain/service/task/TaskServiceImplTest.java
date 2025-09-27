package br.com.alura.AluraFake.domain.service.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.domain.service.course.CourseServiceImpl;
import br.com.alura.AluraFake.domain.strategy.task.TaskStrategyService;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import br.com.alura.AluraFake.util.validation.CourseValidator;
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
class TaskServiceImplTest {

    @Mock
    private TaskOrderService taskOrderService;

    @Mock
    private CourseValidator courseValidator;

    @Mock
    private CourseServiceImpl courseService;

    @Mock
    private TaskStrategyService singleChoiceStrategy;

    @Mock
    private TaskStrategyService multipleChoiceStrategy;

    @Mock
    private TaskStrategyService openTextStrategy;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setup() {
        lenient().when(singleChoiceStrategy.getName()).thenReturn("SINGLE_CHOICE");
        lenient().when(multipleChoiceStrategy.getName()).thenReturn("MULTIPLE_CHOICE");
        lenient().when(openTextStrategy.getName()).thenReturn("OPEN_TEXT");

        taskService = new TaskServiceImpl(
                taskOrderService,
                courseValidator,
                courseService,
                List.of(singleChoiceStrategy, multipleChoiceStrategy, openTextStrategy)
        );
    }

    @Test
    void createTask_should_create_single_choice_task() {
        CreateTaskDTO dto = TaskDummyFactory.createSingleChoiceTaskGeneric();
        Course course = new Course();
        course.setId(dto.getCourseId().longValue());
        course.setStatus(Status.BUILDING);

        when(courseService.getCourseById(anyLong())).thenReturn(course);
        when(singleChoiceStrategy.createTask(eq(dto), eq(course)))
                .thenReturn(TaskDummyFactory.taskResponseDTO());

        TaskResponseDTO response = taskService.createTask(dto);

        assertEquals(TaskDummyFactory.taskResponseDTO(), response);
        verify(taskOrderService).validateAndShiftTasks(course.getId(), dto.getOrder());
        verify(courseValidator).validateStatusBuilding(course.getStatus());
        verify(singleChoiceStrategy).createTask(dto, course);
    }

    @Test
    void createTask_should_create_multiple_choice_task() {
        CreateTaskDTO dto = TaskDummyFactory.createMultipleChoiceTaskGeneric();
        Course course = new Course();
        course.setId(dto.getCourseId().longValue());
        course.setStatus(Status.BUILDING);

        when(courseService.getCourseById(anyLong())).thenReturn(course);
        when(multipleChoiceStrategy.createTask(eq(dto), eq(course)))
                .thenReturn(TaskDummyFactory.taskResponseDTO());

        TaskResponseDTO response = taskService.createTask(dto);

        assertEquals(TaskDummyFactory.taskResponseDTO(), response);
        verify(taskOrderService).validateAndShiftTasks(course.getId(), dto.getOrder());
        verify(courseValidator).validateStatusBuilding(course.getStatus());
        verify(multipleChoiceStrategy).createTask(dto, course);
    }

    @Test
    void createTask_should_create_open_text_task() {
        CreateTaskDTO dto = TaskDummyFactory.createOpenTextTaskGeneric();
        Course course = new Course();
        course.setId(dto.getCourseId().longValue());
        course.setStatus(Status.BUILDING);

        when(courseService.getCourseById(anyLong())).thenReturn(course);
        when(openTextStrategy.createTask(eq(dto), eq(course)))
                .thenReturn(TaskDummyFactory.taskResponseDTOOpenText());

        TaskResponseDTO response = taskService.createTask(dto);

        assertEquals(TaskDummyFactory.taskResponseDTOOpenText(), response);
        verify(taskOrderService).validateAndShiftTasks(course.getId(), dto.getOrder());
        verify(courseValidator).validateStatusBuilding(course.getStatus());
        verify(openTextStrategy).createTask(dto, course);
    }
}