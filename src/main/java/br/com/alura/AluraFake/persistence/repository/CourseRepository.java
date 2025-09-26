package br.com.alura.AluraFake.persistence.repository;

import br.com.alura.AluraFake.domain.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>{

    List<Course> findByInstructorId(Long instructorId);
}