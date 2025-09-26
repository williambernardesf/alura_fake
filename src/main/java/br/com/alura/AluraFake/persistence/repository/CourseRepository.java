package br.com.alura.AluraFake.persistence.repository;

import br.com.alura.AluraFake.domain.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{

}