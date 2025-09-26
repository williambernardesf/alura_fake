package br.com.alura.AluraFake.api.rest.dto.request.taskoption;

import lombok.Data;

@Data
public class CreateTaskOptionGenericDTO {

    private String taskOption;

    private Boolean isCorrect;
}