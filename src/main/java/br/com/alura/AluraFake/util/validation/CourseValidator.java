package br.com.alura.AluraFake.util.validation;

import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.exceptions.InvalidCourseStatusException;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.enums.Type;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class CourseValidator {

    public void validateForPublish(List<Task> tasks, Status status) {
        validateStatusBuilding(status);
        validateHasTasks(tasks);
        validateTaskTypes(tasks);
        validateTaskOrderSequence(tasks);
    }

    public void validateStatusBuilding(Status status) {
        if (status != Status.BUILDING) {
            throw new InvalidCourseStatusException(
                "A operação não é permitida: o curso não está em BUILDING."
            );
        }
    }

    public void validateHasTasks(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalArgumentException("O curso deve ter pelo menos uma atividade.");
        }
    }

    public void validateTaskTypes(List<Task> tasks) {
        boolean hasOpenText = tasks.stream().anyMatch(t -> t.getType() == Type.OPEN_TEXT);
        boolean hasSingleChoice = tasks.stream().anyMatch(t -> t.getType() == Type.SINGLE_CHOICE);
        boolean hasMultipleChoice = tasks.stream().anyMatch(t -> t.getType() == Type.MULTIPLE_CHOICE);

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            throw new IllegalArgumentException("O curso deve ter ao menos uma atividade de cada tipo.");
        }
    }

    public void validateTaskOrderSequence(List<Task> tasks) {
        tasks.sort(Comparator.comparing(Task::getOrderValue));
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getOrderValue() != i + 1) {
                throw new IllegalArgumentException("As atividades devem ter ordem contínua (1, 2, 3...).");
            }
        }
    }
}