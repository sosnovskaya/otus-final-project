package mi.sosnovskaya.services;

import mi.sosnovskaya.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface TaskService {
    Mono<Task> saveTask(Task task);

    Flux<Task> getTasksByPersonId(Long id);

    Mono<Task> startTask(Long id, LocalDateTime time);

    Mono<Task> endTask(Long id, LocalDateTime time);
}
