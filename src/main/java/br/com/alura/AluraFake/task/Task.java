package br.com.alura.AluraFake.task;

import br.com.alura.AluraFake.course.Course;
import br.com.alura.AluraFake.taskoption.TaskOption;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statement;

    @Column(name = "order_value")
    private Integer orderValue;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskOption> taskOptions = new ArrayList<>();

}