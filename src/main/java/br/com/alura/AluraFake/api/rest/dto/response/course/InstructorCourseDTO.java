package br.com.alura.AluraFake.api.rest.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@AllArgsConstructor
public class InstructorCourseDTO {
    private Long id;
    private String title;
    private String status;
    private LocalDateTime publishedAt;
    private int activitiesCount;
}