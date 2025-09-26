package br.com.alura.AluraFake.application.facade.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;

public interface CourseFacade {

    CourseListItemDTO createCourse(NewCourseDTO newCourse);

    InstructorCoursesResponse getCoursesByInstructor(Long id);

    CourseListItemDTO publishCourse(Long id);
}