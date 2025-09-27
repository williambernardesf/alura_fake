package br.com.alura.AluraFake.domain.service.task;

import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskOrderService {

    private static final Logger logger = LoggerFactory.getLogger(TaskOrderService.class);

    private final TaskRepository taskRepository;

    public void validateAndShiftTasks(Long courseId, int newOrder) {
        LogUtils.info(logger, this, "validateAndShiftTasks", "Fetching tasks for course ID: {}", courseId);
        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderValueAsc(courseId);

        LogUtils.info(logger, this, "validateAndShiftTasks", "Tasks before shift:");
        tasks.forEach(t -> LogUtils.info(logger, this, "validateAndShiftTasks", "→ ID: {}, order: {}", t.getId(), t.getOrderValue()));

        int maxAllowedOrder = tasks.size() + 1;
        if (newOrder > maxAllowedOrder) {
            LogUtils.error(logger, this, "validateAndShiftTasks", "Invalid order position: {}, max allowed: {}", newOrder, maxAllowedOrder);
            throw new IllegalArgumentException("Invalid order sequence. Next valid position is " + maxAllowedOrder);
        }

        List<Task> tasksToShift = tasks.stream()
                .filter(t -> t.getOrderValue() >= newOrder)
                .toList();

        if (!tasksToShift.isEmpty()) {
            LogUtils.info(logger, this, "validateAndShiftTasks", "Shifting {} tasks starting from order: {}", tasksToShift.size(), newOrder);

            tasksToShift.forEach(t -> {
                int original = t.getOrderValue();
                t.setOrderValue(original + 1);
                LogUtils.info(logger, this, "validateAndShiftTasks", "Task ID {} shifted from {} to {}", t.getId(), original, t.getOrderValue());
            });

            taskRepository.saveAll(tasksToShift);
            LogUtils.info(logger, this, "validateAndShiftTasks", "Shifted tasks saved");
        } else {
            LogUtils.info(logger, this, "validateAndShiftTasks", "No tasks needed shifting");
        }

        LogUtils.info(logger, this, "validateAndShiftTasks", "Tasks after shift:");
        List<Task> updatedTasks = taskRepository.findByCourseIdOrderByOrderValueAsc(courseId);
        updatedTasks.forEach(t -> LogUtils.info(logger, this, "validateAndShiftTasks", "→ ID: {}, order: {}", t.getId(), t.getOrderValue()));
    }
}