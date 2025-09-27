package br.com.alura.AluraFake.domain.service.task;

import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskOrderService {

    private final TaskRepository taskRepository;

    public void validateAndShiftTasks(Long courseId, int newOrder) {
        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderValueAsc(courseId);

        System.out.println("📦 Tarefas antes do shift:");
        tasks.forEach(t -> System.out.println("→ ID: " + t.getId() + ", ordem: " + t.getOrderValue()));

        if (newOrder > tasks.size() + 1) {
            throw new IllegalArgumentException(
                    "A sequência da ordem está incorreta. Próxima ordem válida é " + (tasks.size() + 1)
            );
        }

        boolean hasConflict = tasks.stream().anyMatch(t -> t.getOrderValue() >= newOrder);
        if (hasConflict) {
            tasks.stream()
                    .filter(t -> t.getOrderValue() >= newOrder)
                    .forEach(t -> {
                        int original = t.getOrderValue();
                        t.setOrderValue(original + 1);
                        System.out.println("🔁 Task ID " + t.getId() + " deslocada de " + original + " para " + t.getOrderValue());
                    });

            taskRepository.saveAll(tasks);
        }

        System.out.println("✅ Tarefas após o shift:");
        tasks.forEach(t -> System.out.println("→ ID: " + t.getId() + ", ordem: " + t.getOrderValue()));
    }
}