package br.com.alura.AluraFake.api.rest.dto.response.course;

import br.com.alura.AluraFake.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseListItemDTO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Status status;
}