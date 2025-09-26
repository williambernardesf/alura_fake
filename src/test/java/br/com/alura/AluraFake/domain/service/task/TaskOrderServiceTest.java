package br.com.alura.AluraFake.domain.service.task;

import br.com.alura.AluraFake.domain.entity.task.Task;
import br.com.alura.AluraFake.persistence.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskOrderServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskOrderService taskOrderService;

    private List<Task> tasks;

    @BeforeEach
    void setup() {
        tasks = new ArrayList<>();
    }

    @Test
    void validateAndShiftTasks_should_do_nothing_when_newOrder_is_next_in_sequence() {
        Task t1 = new Task();
        t1.setOrderValue(1);
        Task t2 = new Task();
        t2.setOrderValue(2);
        tasks.add(t1);
        tasks.add(t2);

        when(taskRepository.findByCourseIdOrderByOrderValueAsc(1L)).thenReturn(tasks);

        assertDoesNotThrow(() -> taskOrderService.validateAndShiftTasks(1L, 3));

        verify(taskRepository, never()).saveAll(any());
    }

    @Test
    void validateAndShiftTasks_should_throw_exception_when_newOrder_is_too_large() {
        Task t1 = new Task();
        t1.setOrderValue(1);
        tasks.add(t1);

        when(taskRepository.findByCourseIdOrderByOrderValueAsc(1L)).thenReturn(tasks);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> taskOrderService.validateAndShiftTasks(1L, 3));

        assertEquals("A sequência da ordem está incorreta. Próxima ordem válida é 2", exception.getMessage());
        verify(taskRepository, never()).saveAll(any());
    }

    @Test
    void validateAndShiftTasks_should_shift_tasks_when_conflict_exists() {
        Task t1 = new Task();
        t1.setOrderValue(1);
        Task t2 = new Task();
        t2.setOrderValue(2);
        Task t3 = new Task();
        t3.setOrderValue(3);
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);

        when(taskRepository.findByCourseIdOrderByOrderValueAsc(1L)).thenReturn(tasks);

        taskOrderService.validateAndShiftTasks(1L, 2);

        assertEquals(1, t1.getOrderValue());
        assertEquals(3, t2.getOrderValue());
        assertEquals(4, t3.getOrderValue());

        verify(taskRepository).saveAll(tasks);
    }
}