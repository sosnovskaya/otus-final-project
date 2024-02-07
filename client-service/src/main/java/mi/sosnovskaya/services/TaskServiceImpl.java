package mi.sosnovskaya.services;

import mi.sosnovskaya.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final WebClient datastoreClient;

    public TaskServiceImpl(WebClient datastoreClient) {
        this.datastoreClient = datastoreClient;
    }

    @Override
    public Mono<Task> saveTask(Task task) {
        log.info("TaskService - saveTask:{}", task);
        return datastoreClient
                .post()
                .uri("/api/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchangeToMono(response -> response.bodyToMono(Task.class));
    }


    @Override
    public Flux<Task> getTasksByPersonId(Long id) {
        log.info("TaskService - getTasksByPersonId:{}", id);
        return datastoreClient
                .get()
                .uri(String.format("/api/tasks/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(Task.class));
    }

    @Override
    public Mono<Task> startTask(Long id, LocalDateTime time) {
        log.info("TaskService - startTask:id {}, time {}", id, time);
        return datastoreClient
                .put()
                .uri(String.format("/api/tasks/%s/start", id))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(time)
                .exchangeToMono(response -> response.bodyToMono(Task.class));
    }

    @Override
    public Mono<Task> endTask(Long id, LocalDateTime time) {
        log.info("TaskService - endTask:id {}, time {}", id, time);
        return datastoreClient
                .put()
                .uri(String.format("/api/tasks/%s/end", id))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(time)
                .exchangeToMono(response -> response.bodyToMono(Task.class));
    }
}
