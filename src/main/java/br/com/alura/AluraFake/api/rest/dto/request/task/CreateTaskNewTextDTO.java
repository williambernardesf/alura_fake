package br.com.alura.AluraFake.api.rest.dto.request.task;

import br.com.alura.AluraFake.domain.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTaskNewTextDTO {

    @NotBlank
    @Size(min = 4, max = 255)
    private String statement;

    @NotNull
    @Positive
    private Integer order;

    @NotNull
    private Integer courseId;

    private Type type;

}