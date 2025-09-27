package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.entity.taskoption.TaskOption;
import br.com.alura.AluraFake.domain.enums.Type;
import br.com.alura.AluraFake.dummies.CourseDummyFactory;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskMapperTest {

    @Spy
    private TaskOptionMapper taskOptionMapper = Mappers.getMapper(TaskOptionMapper.class);

    @InjectMocks
    private TaskMapperImpl mapper;

    @Test
    void shouldMapTaskToDto() {
        Task task = TaskDummyFactory.taskEntity(1);
        task.setType(Type.OPEN_TEXT);

        TaskResponseDTO dto = mapper.toDto(task);

        assertEquals(1L, dto.getCourseId());
        assertEquals("Tarefa 1", dto.getStatement());
        assertEquals("OPEN_TEXT", dto.getType());
    }

    @Test
    void shouldMapTextTaskToCreateTaskDTO() {
        CreateTaskNewTextDTO input = TaskDummyFactory.createOpenTextTask();

        CreateTaskDTO dto = mapper.fromTextTaskToTaskDto(input);

        assertEquals(Type.OPEN_TEXT, dto.getType());
        assertEquals("Escreva sobre programação orientada a objetos", dto.getStatement());
    }

    @Test
    void shouldMapSingleChoiceTaskToCreateTaskDTO() {
        CreateTaskSingleChoiceDTO input = TaskDummyFactory.createSingleChoiceTask();

        CreateTaskDTO dto = mapper.fromSingleTaskToTaskDto(input);

        assertEquals(Type.SINGLE_CHOICE, dto.getType());
        assertEquals("Escolha a alternativa correta", dto.getStatement());
    }

    @Test
    void shouldMapMultipleChoiceTaskToCreateTaskDTO() {
        CreateTaskMultipleChoiceDTO input = TaskDummyFactory.createMultipleChoiceTask();

        CreateTaskDTO dto = mapper.fromMultipleTextToTaskDto(input);

        assertEquals(Type.MULTIPLE_CHOICE, dto.getType());
        assertEquals("Escolha todas as alternativas corretas", dto.getStatement());
    }

    @Test
    void shouldMapToOpenTextEntity() {
        CreateTaskDTO dto = TaskDummyFactory.createOpenTextTaskGeneric();

        Course course = CourseDummyFactory.courseWithCourseId();

        Task entity = mapper.fromOpenTaskToEntity(dto, course, null);

        assertEquals("Responda com suas palavras", entity.getStatement());
        assertEquals(Type.OPEN_TEXT, entity.getType());
        assertEquals(1, entity.getOrderValue());
        assertEquals(course, entity.getCourse());
    }

    @Test
    void shouldMapToSingleChoiceEntity() {
        CreateTaskDTO dto = TaskDummyFactory.createOpenTextTaskGeneric();

        Course course = new Course();
        List<TaskOption> options = List.of(new TaskOption(), new TaskOption());

        Task entity = mapper.fromSingleTaskToEntity(dto, course, options);

        assertEquals(Type.SINGLE_CHOICE, entity.getType());
        assertEquals(1, entity.getOrderValue());
        assertEquals(options, entity.getTaskOptions());
    }

    @Test
    void shouldMapToMultipleChoiceEntity() {
        CreateTaskDTO dto = TaskDummyFactory.createMultipleChoiceTaskGeneric();

        Course course = new Course();
        List<TaskOption> options = List.of(new TaskOption());

        Task entity = mapper.fromMultipleTasktoEntity(dto, course, options);

        assertEquals(Type.MULTIPLE_CHOICE, entity.getType());
        assertEquals(2, entity.getOrderValue());
        assertEquals(options, entity.getTaskOptions());
    }

    @Test
    void shouldMapToEntityWithNoOptions() {
        CreateTaskDTO dto = TaskDummyFactory.createSingleChoiceTaskGeneric();

        Course course = new Course();

        Task entity = mapper.toEntityWithNoOptions(dto, course, null);

        assertEquals(Type.OPEN_TEXT, entity.getType());
        assertEquals(3, entity.getOrderValue());
        assertNotNull(entity.getTaskOptions());
        assertFalse(entity.getTaskOptions().isEmpty());
    }
}