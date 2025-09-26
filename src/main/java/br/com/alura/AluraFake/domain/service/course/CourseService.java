package br.com.alura.AluraFake.domain.service.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;

public interface CourseService {


    CourseListItemDTO createCourse(NewCourseDTO newCourse);

    InstructorCoursesResponse getCoursesByInstructor(Long instructorId);

    CourseListItemDTO publishCourse(Long courseId);
}