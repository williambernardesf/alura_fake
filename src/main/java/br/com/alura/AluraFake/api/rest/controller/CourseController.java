package br.com.alura.AluraFake.api.rest.controller;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.application.facade.course.CourseFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseFacade courseFacade;

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {
        return courseFacade.createCourse(newCourse);
    }

    @GetMapping("/course/all")
    public ResponseEntity<List<CourseListItemDTO>> createCourseAll() {
        return ResponseEntity.ok(courseFacade.createCourseAll());
    }

    @PostMapping("/course/{id}/publish")
    public ResponseEntity<CourseListItemDTO> publishCourse(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courseFacade.publishCourse(id));
    }

}