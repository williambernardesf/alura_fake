package br.com.alura.AluraFake.util.validation;

import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.exceptions.InvalidCourseStatusException;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.enums.Type;
import br.com.alura.AluraFake.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class CourseValidator {

    private static final Logger logger = LoggerFactory.getLogger(CourseValidator.class);

    public void validateForPublish(List<Task> tasks, Status status) {
        LogUtils.info(logger, this, "validateForPublish", "Starting course publish validation");
        validateStatusBuilding(status);
        validateHasTasks(tasks);
        validateTaskTypes(tasks);
        validateTaskOrderSequence(tasks);
        LogUtils.info(logger, this, "validateForPublish", "Course passed all publish validations");
    }

    public void validateStatusBuilding(Status status) {
        LogUtils.info(logger, this, "validateStatusBuilding", "Checking course status: {}", status);
        if (status != Status.BUILDING) {
            LogUtils.error(logger, this, "validateStatusBuilding", "Course status is not BUILDING: {}", status);
            throw new InvalidCourseStatusException("Operation not allowed: course is not in BUILDING status.");
        }
    }

    public void validateHasTasks(List<Task> tasks) {
        LogUtils.info(logger, this, "validateHasTasks", "Checking if course has tasks");
        if (tasks == null || tasks.isEmpty()) {
            LogUtils.error(logger, this, "validateHasTasks", "Course has no tasks");
            throw new IllegalArgumentException("Course must contain at least one task.");
        }
    }

    public void validateTaskTypes(List<Task> tasks) {
        LogUtils.info(logger, this, "validateTaskTypes", "Checking task types coverage");

        boolean hasOpenText = tasks.stream().anyMatch(t -> t.getType() == Type.OPEN_TEXT);
        boolean hasSingleChoice = tasks.stream().anyMatch(t -> t.getType() == Type.SINGLE_CHOICE);
        boolean hasMultipleChoice = tasks.stream().anyMatch(t -> t.getType() == Type.MULTIPLE_CHOICE);

        if (!hasOpenText || !hasSingleChoice || !hasMultipleChoice) {
            LogUtils.error(logger, this, "validateTaskTypes", "Missing required task types: OpenText={}, SingleChoice={}, MultipleChoice={}",
                    hasOpenText, hasSingleChoice, hasMultipleChoice);
            throw new IllegalArgumentException("Course must include at least one task of each type.");
        }
    }

    public void validateTaskOrderSequence(List<Task> tasks) {
        LogUtils.info(logger, this, "validateTaskOrderSequence", "Validating task order sequence");

        tasks.sort(Comparator.comparing(Task::getOrderValue));
        for (int i = 0; i < tasks.size(); i++) {
            int expectedOrder = i + 1;
            int actualOrder = tasks.get(i).getOrderValue();
            if (actualOrder != expectedOrder) {
                LogUtils.error(logger, this, "validateTaskOrderSequence", "Invalid task order at index {}: expected {}, found {}", i, expectedOrder, actualOrder);
                throw new IllegalArgumentException("Tasks must have a continuous order (1, 2, 3...).");
            }
        }
    }
}