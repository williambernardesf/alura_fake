package br.com.alura.AluraFake.dummies;

import br.com.alura.AluraFake.api.rest.dto.request.course.NewCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.CourseListItemDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCourseDTO;
import br.com.alura.AluraFake.api.rest.dto.response.course.InstructorCoursesResponse;
import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class CourseDummyFactory {

    public static Course courseWithCourseId(){
        Course course1 = new Course();
        course1.setId(1L);
        return course1;
    }

    public static Course course(){
        Course course1 = new Course();
        course1.setTitle("Java Completo");
        course1.setDescription("Curso de Java do básico ao avançado");
        course1.setStatus(Status.BUILDING);
        course1.setInstructor(UserDummyFactory.instructor());
        return course1;
    }

    public static Course course2(){
        Course course2 = new Course();
        course2.setTitle("Spring Boot");
        course2.setDescription("Curso completo de Spring Boot");
        course2.setStatus(Status.BUILDING);
        course2.setInstructor(UserDummyFactory.instructor());
        return course2;
    }

    public static NewCourseDTO newCourseDTO() {
        return NewCourseDTO.builder()
                .title("Java Completo")
                .description("Curso de Java do básico ao avançado")
                .emailInstructor("paulo@alura.com.br")
                .build();
    }

    public static CourseListItemDTO courseListItemDTO() {
        return CourseListItemDTO.builder()
                .id(1L)
                .title("Java Completo")
                .description("Curso de Java do básico ao avançado")
                .status(Status.PUBLISHED)
                .build();
    }

    public static InstructorCourseDTO instructorCourseDTO() {
        return InstructorCourseDTO.builder()
                .id(1L)
                .title("Java Completo")
                .status(Status.PUBLISHED.toString())
                .publishedAt(LocalDateTime.of(2025, 1, 1, 10, 0))
                .activitiesCount(2)
                .build();
    }

    public static InstructorCoursesResponse instructorCoursesResponse() {
        return InstructorCoursesResponse.builder()
                .instructorId(1L)
                .instructorName("Paulo")
                .courses(List.of(instructorCourseDTO()))
                .totalPublishedCourses(1L)
                .build();
    }

    public static Course courseWithInstructor(User instructor) {
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Java Completo");
        course.setDescription("Curso de Java do básico ao avançado");
        course.setStatus(Status.BUILDING);
        course.setInstructor(instructor);
        return course;
    }

}