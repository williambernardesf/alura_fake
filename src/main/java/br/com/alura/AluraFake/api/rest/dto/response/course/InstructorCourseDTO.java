package br.com.alura.AluraFake.api.rest.dto.response.course;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorCourseDTO {
    private Long id;
    private String title;
    private String status;
    private LocalDateTime publishedAt;
    private int activitiesCount;
}