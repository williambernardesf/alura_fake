package br.com.alura.AluraFake.api.rest.dto.response.course;

import br.com.alura.AluraFake.domain.enums.Status;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class CourseListItemDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Status status;
}