package ch.redsen.interview.domain.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ch.redsen.interview.domain.model.TodoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.redsen.interview.domain.model.Todo;
import ch.redsen.interview.domain.repository.TodoRepository;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public Map<Long, List<Todo>> getTodosGroupedByUser() {
        return todoRepository.findAll().stream().collect(Collectors
                .groupingBy(Todo::userId));
    }

    public List<Long> getAllUserIdsWithPendingTodos() {
       return todoRepository.findAll().stream()
               .filter(user -> user.status().equals(TodoStatus.PENDING))
               .map(Todo::userId).distinct().toList();
    }

    public boolean hasPendingTodos(Long userId) {
       // return todoRepository.findByUserId(userId).stream()
        //        .filter(user -> user.).count() > 0;
        return todoRepository.findByUserId(userId)
                .stream().anyMatch(user -> user.status().equals(TodoStatus.PENDING));
    }

    public boolean hasCompletedAllTodos(Long userId) {
        throw new UnsupportedOperationException("Unimplemented method 'hasCompletedAllTodos'");
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public List<Todo> getTodosByUser(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
