package br.com.alura.AluraFake.dummies;

import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskMultipleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskNewTextDTO;
import br.com.alura.AluraFake.api.rest.dto.request.task.CreateTaskSingleChoiceDTO;
import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionDTO;
import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import br.com.alura.AluraFake.api.rest.dto.response.task.TaskResponseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.taskoption.TaskOptionResponseDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.entity.taskoption.TaskOption;
import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.domain.enums.Type;

import java.util.List;

public class TaskDummyFactory {

    public static Task emptyTask(){
        return new Task();
    }

    public static CreateTaskNewTextDTO createOpenTextTask() {
        return CreateTaskNewTextDTO.builder()
                .statement("Escreva sobre programação orientada a objetos")
                .order(1)
                .courseId(1)
                .type(Type.OPEN_TEXT)
                .build();
    }

    public static CreateTaskMultipleChoiceDTO createMultipleChoiceTask() {
        return CreateTaskMultipleChoiceDTO.builder()
                .statement("Escolha todas as alternativas corretas")
                .order(2)
                .courseId(1)
                .type(Type.MULTIPLE_CHOICE)
                .taskOptions(List.of(
                        createOption("Opção 1", true),
                        createOption("Opção 2", false),
                        createOption("Opção 3", true)
                ))
                .build();
    }

    public static CreateTaskSingleChoiceDTO createSingleChoiceTask() {
        return CreateTaskSingleChoiceDTO.builder()
                .statement("Escolha a alternativa correta")
                .order(3)
                .courseId(1)
                .type(Type.SINGLE_CHOICE)
                .taskOptions(List.of(
                        createOption("Opção 1", false),
                        createOption("Opção 2", true),
                        createOption("Opção 3", false)
                ))
                .build();
    }

    private static CreateTaskOptionDTO createOption(String optionText, boolean isCorrect) {
        CreateTaskOptionDTO option = new CreateTaskOptionDTO();
        option.setTaskOption(optionText);
        option.setIsCorrect(isCorrect);
        return option;
    }

    public static TaskResponseDTO taskResponseDTO() {
        return TaskResponseDTO.builder()
                .statement("Escolha a alternativa correta")
                .orderValue(3)
                .courseId(1L)
                .type(Type.SINGLE_CHOICE.name())
                .taskOptions(List.of(
                        createOptionResponse("Opção 1", false),
                        createOptionResponse("Opção 2", true),
                        createOptionResponse("Opção 3", false)
                ))
                .build();
    }

    public static TaskResponseDTO taskResponseDTOOpenText() {
        return TaskResponseDTO.builder()
                .statement("Conta como foi sua experiencia")
                .orderValue(1)
                .courseId(53L)
                .type(Type.OPEN_TEXT.name())
                .build();
    }

    public static TaskResponseDTO taskResponseDTOMultipleChoice() {
        return TaskResponseDTO.builder()
                .statement("Escolha todas as alternativas corretas")
                .orderValue(2)
                .courseId(1L)
                .type(Type.MULTIPLE_CHOICE.name())
                .taskOptions(List.of(
                        createOptionResponse("Opção 1", true),
                        createOptionResponse("Opção 2", false),
                        createOptionResponse("Opção 3", true)
                ))
                .build();
    }

    private static TaskOptionResponseDTO createOptionResponse(String text, boolean isCorrect) {
        TaskOptionResponseDTO option = new TaskOptionResponseDTO();
        option.setTaskOption(text);
        option.setIsCorrect(isCorrect);
        return option;
    }

    public static CreateTaskDTO createSingleChoiceTaskGeneric() {
        return CreateTaskDTO.builder()
                .statement("Escolha a alternativa correta")
                .order(3)
                .courseId(1)
                .type(Type.SINGLE_CHOICE)
                .taskOptions(List.of(
                        createOptionGeneric("Opção 1", false),
                        createOptionGeneric("Opção 2", true),
                        createOptionGeneric("Opção 3", false)
                ))
                .build();
    }

    public static CreateTaskDTO createOpenTextTaskGeneric() {
        return CreateTaskDTO.builder()
                .statement("Responda com suas palavras")
                .order(1)
                .courseId(1)
                .type(Type.OPEN_TEXT)
                .build();
    }

    public static CreateTaskDTO createMultipleChoiceTaskGeneric() {
        return CreateTaskDTO.builder()
                .statement("Marque todas as alternativas corretas")
                .order(2)
                .courseId(1)
                .type(Type.MULTIPLE_CHOICE)
                .taskOptions(List.of(
                        createOptionGeneric("Opção A", true),
                        createOptionGeneric("Opção B", false),
                        createOptionGeneric("Opção C", true)
                ))
                .build();
    }

    public static CreateTaskOptionGenericDTO createOptionGeneric(String text, boolean isCorrect) {
        CreateTaskOptionGenericDTO option = new CreateTaskOptionGenericDTO();
        option.setTaskOption(text);
        option.setIsCorrect(isCorrect);
        return option;
    }

    public static Course dummyCourse() {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Java Completo");
        course.setDescription("Curso de Java do básico ao avançado");
        course.setStatus(Status.BUILDING);
        return course;
    }

    public static Task taskEntity(int orderValue) {
        Task task = new Task();
        task.setId((long) orderValue);
        task.setStatement("Tarefa " + orderValue);
        task.setOrderValue(orderValue);
        task.setCourse(dummyCourse());
        return task;
    }

    public static TaskOption taskOption(){
        TaskOption entity = new TaskOption();
        entity.setOption("Opção B");
        entity.setIsCorrect(false);
        return entity;
    }
}