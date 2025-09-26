package br.com.alura.AluraFake.util.validation;

import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TaskOptionValidator {

    public void validateSingleChoice(List<CreateTaskOptionGenericDTO> options, String statement) {
        long correctCount = options.stream().filter(CreateTaskOptionGenericDTO::getIsCorrect).count();

        if (correctCount != 1) {
            throw new IllegalArgumentException("A atividade deve ter exatamente uma alternativa correta.");
        }

        validateDuplicatesAndStatement(options, statement);
    }

    public void validateMultipleChoice(List<CreateTaskOptionGenericDTO> options, String statement) {
        long correctCount = options.stream().filter(CreateTaskOptionGenericDTO::getIsCorrect).count();
        long incorrectCount = options.stream().filter(option -> !option.getIsCorrect()).count();

        if (correctCount < 2 || incorrectCount < 1) {
            throw new IllegalArgumentException("A atividade deve ter no mínimo 2 alternativas corretas e 1 incorreta.");
        }

        validateDuplicatesAndStatement(options, statement);
    }

    private void validateDuplicatesAndStatement(List<CreateTaskOptionGenericDTO> options, String statement) {
        Set<String> descriptions = new HashSet<>();
        for (CreateTaskOptionGenericDTO option : options) {
            String desc = option.getTaskOption().trim();
            if (!descriptions.add(desc.toLowerCase())) {
                throw new IllegalArgumentException("As alternativas não podem ser iguais entre si.");
            }

            if (desc.equalsIgnoreCase(statement.trim())) {
                throw new IllegalArgumentException("As alternativas não podem ser iguais ao enunciado da atividade.");
            }
        }
    }
}