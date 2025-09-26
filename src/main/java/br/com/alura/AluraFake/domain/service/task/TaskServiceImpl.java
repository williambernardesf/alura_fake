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
}