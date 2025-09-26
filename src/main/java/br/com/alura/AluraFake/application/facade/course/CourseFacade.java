package br.com.alura.AluraFake.application.facade.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseFacade {

    ResponseEntity createCourse(NewCourseDTO newCourse);

    List<CourseListItemDTO> createCourseAll();

    CourseListItemDTO publishCourse(Long id);
}