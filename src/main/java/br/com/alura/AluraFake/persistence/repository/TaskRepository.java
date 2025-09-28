package br.com.alura.AluraFake.persistence.repository;

import br.com.alura.AluraFake.domain.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{

    List<Task> findByCourseIdOrderByOrderValueAsc(Long courseId);

    boolean existsByCourseIdAndStatementIgnoreCase(Long courseId, String statement);

}