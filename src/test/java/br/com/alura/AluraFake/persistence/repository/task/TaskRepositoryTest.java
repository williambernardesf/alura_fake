package br.com.alura.AluraFake.persistence.repository.task;

import br.com.alura.AluraFake.domain.entity.course.Course;
import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.domain.entity.user.User;
import br.com.alura.AluraFake.domain.enums.Status;
import br.com.alura.AluraFake.domain.enums.Type;
import br.com.alura.AluraFake.dummies.UserDummyFactory;
import br.com.alura.AluraFake.persistence.repository.CourseRepository;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import br.com.alura.AluraFake.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("github")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private Course course;

    @BeforeEach
    void setup() {
        User instructor = UserDummyFactory.instructor();
        instructor = userRepository.save(instructor);

        course = new Course();
        course.setTitle("Java Completo");
        course.setDescription("Curso de Java do básico ao avançado");
        course.setStatus(Status.BUILDING);
        course.setInstructor(instructor);
        course = courseRepository.save(course);

        Task task1 = new Task();
        task1.setStatement("Instalar JDK");
        task1.setOrderValue(1);
        task1.setCourse(course);
        task1.setType(Type.SINGLE_CHOICE);

        Task task2 = new Task();
        task2.setStatement("Configurar IDE");
        task2.setOrderValue(2);
        task2.setCourse(course);
        task2.setType(Type.SINGLE_CHOICE);

        taskRepository.saveAll(List.of(task1, task2));
    }

    @Test
    void findByCourseIdOrderByOrderValueAsc__should_return_tasks_in_order() {
        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderValueAsc(course.getId());

        tasks.forEach(t -> t.setType(Type.OPEN_TEXT));
        taskRepository.saveAll(tasks);

        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getOrderValue()).isEqualTo(1);
        assertThat(tasks.get(1).getOrderValue()).isEqualTo(2);
        assertThat(tasks).extracting(Task::getStatement)
                .containsExactly("Instalar JDK", "Configurar IDE");
    }


    @Test
    void findByCourseIdOrderByOrderValueAsc__should_return_empty_when_no_tasks() {
        List<Task> tasks = taskRepository.findByCourseIdOrderByOrderValueAsc(999L);
        assertThat(tasks).isEmpty();
    }

    @Test
    void save_task_should_persist_task_with_course() {
        Task newTask = new Task();
        newTask.setStatement("Escrever Hello World");
        newTask.setOrderValue(3);
        newTask.setCourse(course);
        newTask.setType(Type.SINGLE_CHOICE);

        Task saved = taskRepository.save(newTask);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCourse().getId()).isEqualTo(course.getId());
    }
}