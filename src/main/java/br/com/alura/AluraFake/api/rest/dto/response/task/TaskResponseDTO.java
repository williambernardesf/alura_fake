package br.com.alura.AluraFake.api.rest.dto.response.task;

import br.com.alura.AluraFake.api.rest.dto.response.taskoption.TaskOptionResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TaskResponseDTO {
    private String statement;
    private Integer orderValue;
    private String type;
    private Long courseId;

    private List<TaskOptionResponseDTO> taskOptions;
}