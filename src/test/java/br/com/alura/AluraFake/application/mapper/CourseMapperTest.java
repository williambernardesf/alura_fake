package br.com.alura.AluraFake.application.mapper;

import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.api.rest.dto.response.user.UserListItemDTO;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.dummies.CourseDummyFactory;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseMapperTest {

    private final CourseMapper mapper = Mappers.getMapper(CourseMapper.class);

    @Test
    void shouldMapToInstructorCourseDTO() {
        Course course = CourseDummyFactory.courseWithTask();

        InstructorCourseDTO dto = mapper.toInstructorCourseDTO(course);

        assertEquals("BUILDING", dto.getStatus());
        assertEquals(1, dto.getActivitiesCount());
        assertEquals("Java Essentials", dto.getTitle());
    }

    @Test
    void shouldMapToCourseListItemDTO() {
        Course course = CourseDummyFactory.courseWithTask();

        CourseListItemDTO dto = mapper.toCourseListItemDTO(course);

        assertEquals("Java Essentials", dto.getTitle());
        assertEquals("Curso de Java do básico ao avançado", dto.getDescription());
    }

    @Test
    void shouldMapToInstructorCoursesResponse() {
        UserListItemDTO user = UserDummyFactory.instructorDto();

        InstructorCourseDTO c1 = CourseDummyFactory.instructorCourseDTO();
        InstructorCourseDTO c2 = CourseDummyFactory.instructorCourseDTO2();

        List<InstructorCourseDTO> courseDTOs = List.of(c1, c2);

        InstructorCoursesResponse response = mapper.toInstructorCoursesResponse(user, courseDTOs, 1L);

        assertEquals(1L, response.getInstructorId());
        assertEquals("Paulo", response.getInstructorName());
        assertEquals(2, response.getCourses().size());
        assertEquals(1L, response.getTotalPublishedCourses());
    }
}