package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.application.facade.course.CourseFacade;
import br.com.alura.AluraFake.util.LogUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseFacade courseFacade;

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity<CourseListItemDTO> createCourse(@Valid @RequestBody NewCourseDTO newCourse) {
        LogUtils.info(logger, this, "createCourse", "Request received to create course: {}", newCourse.getTitle());
        CourseListItemDTO response = courseFacade.createCourse(newCourse);
        LogUtils.info(logger, this, "createCourse", "Course created with ID: {}, Title: {}", response.getId(), response.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<InstructorCoursesResponse> getCoursesByInstructor(@PathVariable("id") Long id) {
        LogUtils.info(logger, this, "getCoursesByInstructor", "Fetching courses for instructor ID: {}", id);
        InstructorCoursesResponse response = courseFacade.getCoursesByInstructor(id);
        LogUtils.info(logger, this, "getCoursesByInstructor", "Instructor: {}, Courses found: {}", response.getInstructorName(), response.getCourses().size());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/course/{id}/publish")
    public ResponseEntity<CourseListItemDTO> publishCourse(@PathVariable("id") Long id) {
        LogUtils.info(logger, this, "publishCourse", "Publishing course with ID: {}", id);
        CourseListItemDTO response = courseFacade.publishCourse(id);
        LogUtils.info(logger, this, "publishCourse", "Course published: {}, Status: PUBLISHED", response.getTitle());
        return ResponseEntity.ok(response);
    }
}