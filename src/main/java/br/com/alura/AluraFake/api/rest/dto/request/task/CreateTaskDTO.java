package br.com.alura.AluraFake.api.rest.dto.request.task;

import br.com.alura.AluraFake.domain.enums.Type;
import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateTaskDTO {

    private String statement;

    private Integer order;

    private Integer courseId;

    private List<CreateTaskOptionGenericDTO> taskOptions;

    private Type type;

}