package br.com.alura.AluraFake.util.validation;

import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskValidator {

    private static final Logger logger = LoggerFactory.getLogger(TaskValidator.class);

    private final TaskRepository taskRepository;

    public void validateUniqueStatement(Long courseId, String statement) {
        if (taskRepository.existsByCourseIdAndStatementIgnoreCase(courseId, statement)) {
            LogUtils.error(logger, this, "validateUniqueStatement", "Duplicate statement found: {}", statement);
            throw new IllegalArgumentException("There is already a statement registered for this task.");
        }
    }

}