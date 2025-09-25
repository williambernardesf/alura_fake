package br.com.alura.AluraFake.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
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