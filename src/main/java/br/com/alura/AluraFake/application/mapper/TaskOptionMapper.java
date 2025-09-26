package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import br.com.alura.AluraFake.domain.entity.taskoption.TaskOption;
import br.com.alura.AluraFake.api.rest.dto.response.taskoption.TaskOptionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskOptionMapper {

    @Mapping(source = "option", target = "taskOption")
    TaskOptionResponseDTO toTaskOptionDto(TaskOption taskOption);

    @Mapping(source = "taskOption", target = "option")
    @Mapping(source = "isCorrect", target = "isCorrect")
    TaskOption toTaskOption(CreateTaskOptionGenericDTO createTaskOptionGenericDTO);

    List<TaskOption> fromGenericToListEntity(List<CreateTaskOptionGenericDTO> dtos);

}