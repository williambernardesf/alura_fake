package br.com.alura.AluraFake.application.facade.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.domain.service.course.CourseService;
import br.com.alura.AluraFake.dummies.CourseDummyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseFacadeImplTest {

    private CourseService courseService;
    private CourseFacadeImpl courseFacade;

    @BeforeEach
    void setup() {
        courseService = mock(CourseService.class);
        courseFacade = new CourseFacadeImpl(courseService);
    }

    @Test
    void createCourse_should_delegate_to_service_and_return_dto() {
        NewCourseDTO newCourseDTO = CourseDummyFactory.newCourseDTO();
        CourseListItemDTO dto = CourseDummyFactory.courseListItemDTO();

        when(courseService.createCourse(any(NewCourseDTO.class))).thenReturn(dto);

        CourseListItemDTO result = courseFacade.createCourse(newCourseDTO);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getTitle(), result.getTitle());
        verify(courseService, times(1)).createCourse(newCourseDTO);
    }

    @Test
    void getCoursesByInstructor_should_delegate_to_service_and_return_response() {
        Long instructorId = 1L;
        InstructorCoursesResponse response = CourseDummyFactory.instructorCoursesResponse();

        when(courseService.getCoursesByInstructor(instructorId)).thenReturn(response);

        InstructorCoursesResponse result = courseFacade.getCoursesByInstructor(instructorId);

        assertEquals(response.getInstructorId(), result.getInstructorId());
        assertEquals(response.getInstructorName(), result.getInstructorName());
        verify(courseService, times(1)).getCoursesByInstructor(instructorId);
    }

    @Test
    void publishCourse_should_delegate_to_service_and_return_dto() {
        Long courseId = 1L;
        CourseListItemDTO dto = CourseDummyFactory.courseListItemDTO();

        when(courseService.publishCourse(courseId)).thenReturn(dto);

        CourseListItemDTO result = courseFacade.publishCourse(courseId);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getTitle(), result.getTitle());
        verify(courseService, times(1)).publishCourse(courseId);
    }
}