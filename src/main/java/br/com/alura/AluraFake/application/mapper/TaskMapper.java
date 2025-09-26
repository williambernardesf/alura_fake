package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.entity.taskoption.TaskOption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = { TaskOptionMapper.class })
public interface TaskMapper {
    @Mapping(source = "course.id", target = "courseId")
    TaskResponseDTO toDto(Task task);

    @Mapping(target = "type", constant = "OPEN_TEXT")
    CreateTaskDTO fromTextTaskToTaskDto(CreateTaskNewTextDTO createTaskNewTextDTO);

    @Mapping(target = "type", constant = "SINGLE_CHOICE")
    CreateTaskDTO fromSingleTaskToTaskDto(CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO);

    @Mapping(target = "type", constant = "MULTIPLE_CHOICE")
    CreateTaskDTO fromMultipleTextToTaskDto(CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO);

    @Mapping(source = "createTaskNewTextDTO.order", target = "orderValue")
    @Mapping(source = "course", target = "course")
    @Mapping(target = "type", constant = "OPEN_TEXT")
    Task fromOpenTaskToEntity(CreateTaskDTO createTaskNewTextDTO, Course course, List<TaskOption> taskOptions);

    @Mapping(source = "createTaskSingleChoiceDTO.order", target = "orderValue")
    @Mapping(source = "course", target = "course")
    @Mapping(target = "type", constant = "SINGLE_CHOICE")
    @Mapping(source = "taskOptions", target = "taskOptions")
    Task fromSingleTaskToEntity(CreateTaskDTO createTaskSingleChoiceDTO, Course course, List<TaskOption> taskOptions);

    @Mapping(source = "createTaskMultipleChoiceDTO.order", target = "orderValue")
    @Mapping(source = "course", target = "course")
    @Mapping(target = "type", constant = "MULTIPLE_CHOICE")
    @Mapping(source = "taskOptions", target = "taskOptions")
    Task fromMultipleTasktoEntity(CreateTaskDTO createTaskMultipleChoiceDTO, Course course, List<TaskOption> taskOptions);

    default Task toEntityWithNoOptions(CreateTaskDTO dto, Course course, List<TaskOption> taskOptions) {
        Task task = fromOpenTaskToEntity(dto, course, taskOptions);
        if (task.getTaskOptions() == null) {
            task.setTaskOptions(new ArrayList<>());
        }
        return task;
    }
}