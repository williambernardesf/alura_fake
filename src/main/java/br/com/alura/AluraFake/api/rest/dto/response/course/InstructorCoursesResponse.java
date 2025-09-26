package br.com.alura.AluraFake.api.rest.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InstructorCoursesResponse {
    private Long instructorId;
    private String instructorName;
    private List<InstructorCourseDTO> courses;
    private long totalPublishedCourses;
}