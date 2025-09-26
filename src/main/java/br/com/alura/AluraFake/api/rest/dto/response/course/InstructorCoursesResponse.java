package br.com.alura.AluraFake.api.rest.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
public class InstructorCoursesResponse {
    private Long instructorId;
    private String instructorName;
    private List<InstructorCourseDTO> courses;
    private long totalPublishedCourses;
}