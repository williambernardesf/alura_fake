package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "status", expression = "java(course.getStatus().name())")
    @Mapping(target = "activitiesCount", expression = "java(course.getTasks() != null ? course.getTasks().size() : 0)")
    InstructorCourseDTO toInstructorCourseDTO(Course course);

    List<InstructorCourseDTO> toInstructorCourseDTOList(List<Course> courses);
    CourseListItemDTO toCourseListItemDTO(Course course);

    @Mapping(target = "instructorId", source = "user.id")
    @Mapping(target = "instructorName", source = "user.name")
    @Mapping(target = "courses", source = "courseDTOs")
    @Mapping(target = "totalPublishedCourses", source = "totalPublished")
    InstructorCoursesResponse toInstructorCoursesResponse(UserListItemDTO user,
                                                          List<InstructorCourseDTO> courseDTOs,
                                                          long totalPublished);
}