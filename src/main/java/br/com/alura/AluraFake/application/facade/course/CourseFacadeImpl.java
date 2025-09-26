package br.com.alura.AluraFake.application.facade.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.domain.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    private final CourseService courseService;

    @Override
    public ResponseEntity createCourse(NewCourseDTO newCourse){
        return courseService.createCourse(newCourse);
    }

    @Override
    public InstructorCoursesResponse getCoursesByInstructor(Long instructorId){
        return courseService.getCoursesByInstructor(instructorId);
    }
    @Override
    public CourseListItemDTO publishCourse(Long courseId) {
        return courseService.publishCourse(courseId);
    }
}