package mi.sosnovskaya.service;

import mi.sosnovskaya.domain.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface TaskService {
    Flux<Task> getAllTasks();

    Flux<Task> getAllTasksByPersonId(Long personId);

    Mono<Task> getTasksById(Long id);

    Mono<Task> saveTask(Task task);

    Mono<Task> setStartTime(Long taskId, LocalDateTime time);

    Mono<Task> setEndTime(Long taskId, LocalDateTime time);
}
