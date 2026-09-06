package ch.redsen.interview.domain.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import ch.redsen.interview.domain.model.Todo;
import ch.redsen.interview.domain.model.TodoStatus;
import ch.redsen.interview.domain.repository.TodoRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void shouldGetTodosGroupedByUser() {
        // Given
        when(todoRepository.findAll())
            .thenReturn(
                List.of(
                    new Todo(1L, "Title 1", "Description 1", TodoStatus.PENDING, LocalDateTime.now().plusDays(1), 1L),
                    new Todo(2L, "Title 2", "Description 2", TodoStatus.COMPLETED, LocalDateTime.now().plusDays(2), 1L),
                    new Todo(3L, "Title 3", "Description 3", TodoStatus.PENDING, LocalDateTime.now().plusDays(3), 1L),
                    new Todo(4L, "Title 4", "Description 4", TodoStatus.PENDING, LocalDateTime.now().plusDays(4), 2L),
                    new Todo(5L, "Title 5", "Description 5", TodoStatus.PENDING, LocalDateTime.now().plusDays(4), 2L),
                    new Todo(6L, "Title 6", "Description 6", TodoStatus.COMPLETED, LocalDateTime.now().plusDays(5), 3L)
                ));
        // When
        Map<Long, List<Todo>> result = todoService.getTodosGroupedByUser();
        // Then
        verify(todoRepository).findAll();
        assertThat(result).hasSize(3);
        assertThat(result).containsKey(1L);
        assertThat(result).containsKey(2L);
        assertThat(result).containsKey(3L);
        assertThat(result.get(1L)).hasSize(3);
        assertThat(result.get(2L)).hasSize(2);
        assertThat(result.get(3L)).hasSize(1);
    }

    @Test
    void shouldGetAllUserIdsWithPendingTodos() {
        // Given
        when(todoRepository.findAll())
            .thenReturn(
                List.of(
                    new Todo(1L, "Title 1", "Description 1", TodoStatus.PENDING, LocalDateTime.now().plusDays(1), 1L),
                    new Todo(2L, "Title 2", "Description 2", TodoStatus.COMPLETED, LocalDateTime.now().plusDays(2), 1L),
                    new Todo(3L, "Title 3", "Description 3", TodoStatus.PENDING, LocalDateTime.now().plusDays(3), 1L),
                    new Todo(4L, "Title 4", "Description 4", TodoStatus.PENDING, LocalDateTime.now().plusDays(4), 2L),
                    new Todo(5L, "Title 5", "Description 5", TodoStatus.PENDING, LocalDateTime.now().plusDays(4), 2L),
                    new Todo(6L, "Title 6", "Description 6", TodoStatus.COMPLETED, LocalDateTime.now().plusDays(5), 3L)
                ));
        // When
        List<Long> result = todoService.getAllUserIdsWithPendingTodos();
        // Then
        verify(todoRepository).findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(1L, 2L);
    }


}
