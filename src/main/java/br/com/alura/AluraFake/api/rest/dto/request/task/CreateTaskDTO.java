package br.com.alura.AluraFake.api.rest.dto.request.task;

import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import br.com.alura.AluraFake.domain.enums.Type;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateTaskDTO {

    private String statement;

    private Integer order;

    private Integer courseId;

    private List<CreateTaskOptionGenericDTO> taskOptions;

    private Type type;

}