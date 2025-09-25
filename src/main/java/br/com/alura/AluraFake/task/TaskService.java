package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.CourseRepository;
import br.com.alura.AluraFake.course.Status;
import br.com.alura.AluraFake.exceptions.InvalidCourseStatusException;
import br.com.alura.AluraFake.taskoption.CreateTaskOptionDTO;
import br.com.alura.AluraFake.taskoption.TaskOption;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public ResponseEntity<?> createOpenTextExercise(CreateTaskNewTextDTO dto) {
        var course = courseRepository.findById(dto.getCourseId().longValue())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        validateCourseStatus(course.getStatus());

        validateOrderOfTask(course.getId(), dto.getOrder());

        Task task = Task.builder()
                .statement(dto.getStatement())
                .orderValue(dto.getOrder())
                .course(course)
                .type(Type.OPEN_TEXT)
                .build();

        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    public ResponseEntity<?> createSingleChoice(CreateTaskSingleChoiceDTO createTaskSingleChoiceDTO) {
        var course = courseRepository.findById(createTaskSingleChoiceDTO.getCourseId().longValue())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        validateCourseStatus(course.getStatus());
        validateOptionsSingleChoice(createTaskSingleChoiceDTO.getTaskOptions(), createTaskSingleChoiceDTO.getStatement());
        validateOrderOfTask(course.getId(), createTaskSingleChoiceDTO.getOrder());

        List<TaskOption> taskOptions = createTaskSingleChoiceDTO.getTaskOptions().stream()
                .map(dto -> TaskOption.builder()
                        .option(dto.getTaskOption())
                        .isCorrect(dto.getIsCorrect())
                        .build())
                .toList();

        Task task = Task.builder()
                .statement(createTaskSingleChoiceDTO.getStatement())
                .orderValue(createTaskSingleChoiceDTO.getOrder())
                .course(course)
                .type(Type.SINGLE_CHOICE)
                .taskOptions(taskOptions)
                .build();

        taskOptions.forEach(taskOption -> taskOption.setTask(task));

        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<?> createMultipleChoice(CreateTaskMultipleChoiceDTO createTaskMultipleChoiceDTO) {
        var course = courseRepository.findById(createTaskMultipleChoiceDTO.getCourseId().longValue())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        validateCourseStatus(course.getStatus());
        validateOptionsMultipleChoice(createTaskMultipleChoiceDTO.getTaskOptions(), createTaskMultipleChoiceDTO.getStatement());
        validateOrderOfTask(course.getId(), createTaskMultipleChoiceDTO.getOrder());

        List<TaskOption> taskOptions = createTaskMultipleChoiceDTO.getTaskOptions().stream()
                .map(dto -> TaskOption.builder()
                        .option(dto.getTaskOption())
                        .isCorrect(dto.getIsCorrect())
                        .build())
                .toList();

        Task task = Task.builder()
                .statement(createTaskMultipleChoiceDTO.getStatement())
                .orderValue(createTaskMultipleChoiceDTO.getOrder())
                .course(course)
                .type(Type.MULTIPLE_CHOICE)
                .taskOptions(taskOptions)
                .build();

        taskOptions.forEach(taskOption -> taskOption.setTask(task));

        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    private void validateCourseStatus(Status status) {
        if (!status.equals(Status.BUILDING)) {
            throw new InvalidCourseStatusException("Não é possível criar tarefa: o curso não está em BUILDING.");
        }
    }

    private void validateOptionsSingleChoice(List<CreateTaskOptionDTO> options, String statement) {
        long correctCount = options.stream().filter(CreateTaskOptionDTO::getIsCorrect).count();

        if (correctCount != 1) {
            throw new IllegalArgumentException("A atividade deve ter exatamente uma alternativa correta.");
        }

        Set<String> descriptions = new HashSet<>();
        for (CreateTaskOptionDTO option : options) {
            String desc = option.getTaskOption().trim();
            if (!descriptions.add(desc.toLowerCase())) {
                throw new IllegalArgumentException("As alternativas não podem ser iguais entre si.");
            }

            if (desc.equalsIgnoreCase(statement.trim())) {
                throw new IllegalArgumentException("As alternativas não podem ser iguais ao enunciado da atividade.");
            }
        }
    }

    private void validateOptionsMultipleChoice(List<CreateTaskOptionDTO> options, String statement) {
        long correctCount = options.stream().filter(CreateTaskOptionDTO::getIsCorrect).count();
        long incorrectCount = options.stream().filter(option -> !option.getIsCorrect()).count();

        if (correctCount < 2 || incorrectCount < 1) {
            throw new IllegalArgumentException("A atividade deve ter no mínimo 2 alternativas corretas e 1 incorreta.");
        }

        Set<String> descriptions = new HashSet<>();
        for (CreateTaskOptionDTO option : options) {
            String desc = option.getTaskOption().trim();
            if (!descriptions.add(desc.toLowerCase())) {
                throw new IllegalArgumentException("As alternativas não podem ser iguais entre si.");
            }

            if (desc.equalsIgnoreCase(statement.trim())) {
                throw new IllegalArgumentException("As alternativas não podem ser iguais ao enunciado da atividade.");
            }
        }
    }

    private void validateOrderOfTask(Long courseId, Integer newOrder) {
        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderValueAsc(courseId);

        if (newOrder > tasks.size() + 1) {
            throw new IllegalArgumentException(
                    "A sequência da ordem está incorreta. Próxima ordem válida é " + (tasks.size() + 1)
            );
        }

        boolean hasConflict = tasks.stream().anyMatch(t -> t.getOrderValue() >= newOrder);
        if (hasConflict) {
            tasks.stream()
                    .filter(t -> t.getOrderValue() >= newOrder)
                    .forEach(t -> t.setOrderValue(t.getOrderValue() + 1));
            taskRepository.saveAll(tasks);
        }
    }

}