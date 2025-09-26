package br.com.alura.AluraFake.persistence.repository.course;

import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.persistence.repository.CourseRepository;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private User instructor;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setup() {
        instructor = UserDummyFactory.instructor();
        instructor = userRepository.save(instructor);

        course1 = new Course();
        course1.setTitle("Java Completo");
        course1.setDescription("Curso de Java do básico ao avançado");
        course1.setStatus(Status.BUILDING);
        course1.setInstructor(instructor);

        course2 = new Course();
        course2.setTitle("Spring Boot");
        course2.setDescription("Curso completo de Spring Boot");
        course2.setStatus(Status.BUILDING);
        course2.setInstructor(instructor);

        courseRepository.saveAll(List.of(course1, course2));
    }

    @Test
    void findByInstructorId__should_return_courses_of_instructor() {
        List<Course> courses = courseRepository.findByInstructorId(instructor.getId());

        assertThat(courses).hasSize(2);
        assertThat(courses).extracting("title").containsExactlyInAnyOrder("Java Completo", "Spring Boot");
        assertThat(courses).allMatch(c -> c.getInstructor().getId().equals(instructor.getId()));
    }

    @Test
    void findByInstructorId__should_return_empty_when_no_courses() {
        List<Course> courses = courseRepository.findByInstructorId(999L);
        assertThat(courses).isEmpty();
    }

    @Test
    void save_course_should_persist_course_with_instructor() {
        Course newCourse = new Course();
        newCourse.setTitle("Kotlin Básico");
        newCourse.setDescription("Curso introdutório de Kotlin");
        newCourse.setStatus(Status.BUILDING);
        newCourse.setInstructor(instructor);

        Course saved = courseRepository.save(newCourse);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getInstructor().getId()).isEqualTo(instructor.getId());
    }

    @Test
    void findById_should_return_course_when_exists() {
        Optional<Course> found = courseRepository.findById(course1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Java Completo");
    }
}