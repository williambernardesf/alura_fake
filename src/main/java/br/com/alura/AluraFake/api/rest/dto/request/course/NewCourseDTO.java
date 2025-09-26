package br.com.alura.AluraFake.api.rest.dto.request.course;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class NewCourseDTO {

    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    @Length(min = 4, max = 255)
    private String description;
    @NotNull
    @NotBlank
    @Email
    private String emailInstructor;
}