package br.com.alura.AluraFake.api.rest.dto.request.task;

import br.com.alura.AluraFake.domain.enums.Type;
import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateTaskMultipleChoiceDTO {

    @NotBlank
    @Size(min = 4, max = 255, message = "O enunciado deve ter no mínimo 4 e no máximo 255 caracteres")
    private String statement;

    @NotNull
    @Positive
    private Integer order;

    @NotNull
    private Integer courseId;

    @NotNull
    @Size(min = 3, max = 5, message = "A atividade deve ter entre 2 e 5 alternativas.")
    private List<CreateTaskOptionDTO> taskOptions;

    private Type type;

}