package br.com.alura.AluraFake.util.validation;

import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import br.com.alura.AluraFake.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TaskOptionValidator {

    private static final Logger logger = LoggerFactory.getLogger(TaskOptionValidator.class);

    public void validateSingleChoice(List<CreateTaskOptionGenericDTO> options, String statement) {
        LogUtils.info(logger, this, "validateSingleChoice", "Validating single choice task for statement: {}", statement);

        long correctCount = options.stream().filter(CreateTaskOptionGenericDTO::getIsCorrect).count();
        if (correctCount != 1) {
            LogUtils.error(logger, this, "validateSingleChoice", "Invalid number of correct options: {}", correctCount);
            throw new IllegalArgumentException("Task must have exactly one correct option.");
        }

        validateDuplicatesAndStatement(options, statement);
        LogUtils.info(logger, this, "validateSingleChoice", "Single choice task passed validation");
    }

    public void validateMultipleChoice(List<CreateTaskOptionGenericDTO> options, String statement) {
        LogUtils.info(logger, this, "validateMultipleChoice", "Validating multiple choice task for statement: {}", statement);

        long correctCount = options.stream().filter(CreateTaskOptionGenericDTO::getIsCorrect).count();
        long incorrectCount = options.stream().filter(option -> !option.getIsCorrect()).count();

        if (correctCount < 2 || incorrectCount < 1) {
            LogUtils.error(logger, this, "validateMultipleChoice", "Invalid correct/incorrect count: correct={}, incorrect={}", correctCount, incorrectCount);
            throw new IllegalArgumentException("Task must have at least 2 correct options and 1 incorrect.");
        }

        validateDuplicatesAndStatement(options, statement);
        LogUtils.info(logger, this, "validateMultipleChoice", "Multiple choice task passed validation");
    }

    private void validateDuplicatesAndStatement(List<CreateTaskOptionGenericDTO> options, String statement) {
        LogUtils.info(logger, this, "validateDuplicatesAndStatement", "Checking for duplicate options and match with statement");

        Set<String> descriptions = new HashSet<>();
        for (CreateTaskOptionGenericDTO option : options) {
            String desc = option.getTaskOption().trim();
            String normalized = desc.toLowerCase();

            if (!descriptions.add(normalized)) {
                LogUtils.error(logger, this, "validateDuplicatesAndStatement", "Duplicate option found: {}", desc);
                throw new IllegalArgumentException("Options must not be duplicated.");
            }

            if (normalized.equals(statement.trim().toLowerCase())) {
                LogUtils.error(logger, this, "validateDuplicatesAndStatement", "Option matches statement: {}", desc);
                throw new IllegalArgumentException("Options must not match the task statement.");
            }
        }

        LogUtils.info(logger, this, "validateDuplicatesAndStatement", "No duplicates or statement conflicts found");
    }
}