package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.application.facade.course.CourseFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseFacade courseFacade;

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity<CourseListItemDTO> createCourse(@Valid @RequestBody NewCourseDTO newCourse) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseFacade.createCourse(newCourse));
    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<InstructorCoursesResponse> getCoursesByInstructor(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseFacade.getCoursesByInstructor(id));
    }

    @PostMapping("/course/{id}/publish")
    public ResponseEntity<CourseListItemDTO> publishCourse(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseFacade.publishCourse(id));
    }

}