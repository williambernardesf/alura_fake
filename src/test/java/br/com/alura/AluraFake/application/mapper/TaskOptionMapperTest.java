package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.request.taskoption.CreateTaskOptionGenericDTO;
import br.com.alura.AluraFake.api.rest.dto.response.taskoption.TaskOptionResponseDTO;
import br.com.alura.AluraFake.domain.entity.taskoption.TaskOption;
import br.com.alura.AluraFake.dummies.TaskDummyFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskOptionMapperTest {

    private final TaskOptionMapper mapper = Mappers.getMapper(TaskOptionMapper.class);

    @Test
    void shouldMapDtoToEntity_usingDummy() {
        CreateTaskOptionGenericDTO dto = TaskDummyFactory.createOptionGeneric("Opção A", true);

        TaskOption entity = mapper.toTaskOption(dto);

        assertEquals("Opção A", entity.getOption());
        assertTrue(entity.getIsCorrect());
    }

    @Test
    void shouldMapEntityToDto_usingDummy() {
        TaskOption entity = TaskDummyFactory.taskOption();

        TaskOptionResponseDTO dto = mapper.toTaskOptionDto(entity);

        assertEquals("Opção B", dto.getTaskOption());
        assertFalse(dto.getIsCorrect());
    }

    @Test
    void shouldMapListOfDtosToEntities_usingDummy() {
        List<CreateTaskOptionGenericDTO> dtos = TaskDummyFactory.createMultipleChoiceTaskGeneric().getTaskOptions();

        List<TaskOption> entities = mapper.fromGenericToListEntity(dtos);

        assertEquals(3, entities.size());
        assertEquals("Opção A", entities.get(0).getOption());
        assertTrue(entities.get(0).getIsCorrect());
        assertEquals("Opção B", entities.get(1).getOption());
        assertFalse(entities.get(1).getIsCorrect());
        assertEquals("Opção C", entities.get(2).getOption());
        assertTrue(entities.get(2).getIsCorrect());
    }
}