package br.com.alura.AluraFake.api.rest.dto.request.taskoption;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateTaskOptionGenericDTO {

    private String taskOption;

    private Boolean isCorrect;
}