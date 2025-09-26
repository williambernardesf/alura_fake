package br.com.alura.AluraFake.api.rest.dto.request.taskoption;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateTaskOptionDTO {

    @NotBlank
    @Size(min = 4, max = 80, message = "Cada alternativa deve ter entre 4 e 80 caracteres.")
    private String taskOption;

    @NotNull
    private Boolean isCorrect;

}