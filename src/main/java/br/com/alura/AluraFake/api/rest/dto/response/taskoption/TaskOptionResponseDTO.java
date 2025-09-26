package br.com.alura.AluraFake.api.rest.dto.response.taskoption;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class TaskOptionResponseDTO {

    private String taskOption;

    private Boolean isCorrect;

}