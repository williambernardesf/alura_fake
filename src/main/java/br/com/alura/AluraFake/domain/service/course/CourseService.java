package br.com.alura.AluraFake.domain.service.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import org.springframework.http.ResponseEntity;

public interface CourseService {

    ResponseEntity createCourse(NewCourseDTO newCourse);

    InstructorCoursesResponse getCoursesByInstructor(Long instructorId);

    CourseListItemDTO publishCourse(Long courseId);
}