package br.com.alura.AluraFake.application.facade.course;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.domain.service.course.CourseService;
import br.com.alura.AluraFake.util.LogUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    private static final Logger logger = LoggerFactory.getLogger(CourseFacadeImpl.class);

    private final CourseService courseService;

    @Override
    public CourseListItemDTO createCourse(NewCourseDTO newCourse){
        LogUtils.info(logger, this, "createCourse", "Creating course with title: {}", newCourse.getTitle());
        CourseListItemDTO response = courseService.createCourse(newCourse);
        LogUtils.info(logger, this, "createCourse", "Course created with ID: {}, Title: {}", response.getId(), response.getTitle());
        return response;
    }

    @Override
    public InstructorCoursesResponse getCoursesByInstructor(Long instructorId){
        LogUtils.info(logger, this, "getCoursesByInstructor", "Fetching courses for instructor ID: {}", instructorId);
        InstructorCoursesResponse response = courseService.getCoursesByInstructor(instructorId);
        LogUtils.info(logger, this, "getCoursesByInstructor", "Instructor: {}, Courses found: {}", response.getInstructorName(), response.getCourses().size());
        return response;
    }

    @Override
    public CourseListItemDTO publishCourse(Long courseId) {
        LogUtils.info(logger, this, "publishCourse", "Publishing course with ID: {}", courseId);
        CourseListItemDTO response = courseService.publishCourse(courseId);
        LogUtils.info(logger, this, "publishCourse", "Course published: {}, Status: PUBLISHED", response.getTitle());
        return response;
    }
}