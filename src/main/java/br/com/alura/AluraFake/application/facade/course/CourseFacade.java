package br.com.alura.AluraFake.application.facade.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import org.springframework.http.ResponseEntity;

public interface CourseFacade {

    ResponseEntity createCourse(NewCourseDTO newCourse);

    InstructorCoursesResponse getCoursesByInstructor(Long id);

    CourseListItemDTO publishCourse(Long id);
}