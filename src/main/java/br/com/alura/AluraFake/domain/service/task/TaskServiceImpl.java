package br.com.alura.AluraFake.domain.service.task;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.domain.service.course.CourseServiceImpl;
import br.com.alura.AluraFake.domain.strategy.task.TaskStrategyService;
import br.com.alura.AluraFake.util.validation.CourseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService{
    private final TaskOrderService taskOrderService;
    private final CourseValidator courseValidator;
    private final CourseServiceImpl courseService;
    private final List<TaskStrategyService> strategyServiceList;

    @Override
    public TaskResponseDTO createTask(CreateTaskDTO createTaskDTO){
        var course = courseService.getCourseById(createTaskDTO.getCourseId().longValue());
        courseValidator.validateStatusBuilding(course.getStatus());
        taskOrderService.validateAndShiftTasks(course.getId(), createTaskDTO.getOrder());

        return this.strategyServiceList.stream()
                .filter(strategy -> strategy.getName().equalsIgnoreCase(createTaskDTO.getType().toString()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unexpected value:" + createTaskDTO.getType().toString()))
                .createTask(createTaskDTO, course);
    }

//    public TaskResponseDTO createOpenTextExercise(CreateTaskNewTextDTO createTaskNewTextDTO) {
//        var course = courseService.getCourseById(createTaskNewTextDTO.getCourseId().longValue());
//
//        var taskkk = taskMapper.fromTextTaskToTaskDto(createTaskNewTextDTO);
//
//        courseValidator.validateStatusBuilding(course.getStatus());
//
//        taskOrderService.validateAndShiftTasks(course.getId(), createTaskNewTextDTO.getOrder());
//
//        var task = taskMapper.toEntityWithNoOptions(createTaskNewTextDTO, course, null);
//
//        var savedTask = taskRepository.save(task);
//
//        return taskMapper.toDto(savedTask);
//    }
//
//    public TaskResponseDTO createSingleChoice(CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
//        var course = courseService.getCourseById(createTaskSingleChoiceDTO.getCourseId().longValue());
//
//        courseValidator.validateStatusBuilding(course.getStatus());
//        taskOptionValidator.validateSingleChoice(createTaskSingleChoiceDTO.getTaskOptions(), createTaskSingleChoiceDTO.getStatement());
//        taskOrderService.validateAndShiftTasks(course.getId(), createTaskSingleChoiceDTO.getOrder());
//
//        var taskOptions = taskOptionMapper.toListEntity(createTaskSingleChoiceDTO.getTaskOptions());
//
//        var task = taskMapper.toEntitySingle(createTaskSingleChoiceDTO, course, taskOptions);
//
//        taskOptions.forEach(taskOption -> taskOption.setTask(task));
//
//        var savedTask = taskRepository.save(task);
//
//        return taskMapper.toDto(savedTask);
//    }

//    public TaskResponseDTO createMultipleChoice(CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
//        var course = courseService.getCourseById(createTaskMultipleChoiceDTO.getCourseId().longValue());
//
//        courseValidator.validateStatusBuilding(course.getStatus());
//        taskOptionValidator.validateMultipleChoice(createTaskMultipleChoiceDTO.getTaskOptions(), createTaskMultipleChoiceDTO.getStatement()); // delega
//        taskOrderService.validateAndShiftTasks(course.getId(), createTaskMultipleChoiceDTO.getOrder());
//
//        var taskOptions = taskOptionMapper.toListEntity(createTaskMultipleChoiceDTO.getTaskOptions());
//
//        var task = taskMapper.toEntityMultiple(createTaskMultipleChoiceDTO, course, taskOptions);
//
//        taskOptions.forEach(taskOption -> taskOption.setTask(task));
//
//        var savedTask = taskRepository.save(task);
//
//        return taskMapper.toDto(savedTask);
//    }
}