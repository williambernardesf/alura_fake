package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseListItemDTO toCourseListItemDTO(Course course);
}