package mi.sosnovskaya.api;

import mi.sosnovskaya.domain.Task;
import mi.sosnovskaya.domain.TaskDto;
import mi.sosnovskaya.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;
    private final Scheduler workerPool;

    public TaskController(TaskService taskService, Scheduler workerPool) {
        this.taskService = taskService;
        this.workerPool = workerPool;
    }

    @PostMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<TaskDto> saveTask(@RequestBody TaskDto taskDto) {
        return Mono.just(new Task(taskDto.name(), taskDto.description(), taskDto.personId(), taskDto.startTime(), taskDto.endTime()))
                .doOnNext(task -> log.info("TaskController - saveTask:{}", task))
                .flatMap(taskService::saveTask)
                .publishOn(workerPool)
                .doOnNext(taskSaved -> log.info("task saved:{}", taskSaved))
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getPersonId(), task.getStartTime(), task.getEndTime()))
                .subscribeOn(workerPool);
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<TaskDto> getAllTask() {
        log.info("TaskController - getAllTask");
        return taskService.getAllTasks()
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getPersonId(), task.getStartTime(), task.getEndTime()))
                .doOnNext(taskDto -> log.info("taskDto:{}", taskDto))
                .subscribeOn(workerPool);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<TaskDto> getTaskById(@PathVariable("id") Long id) {
        log.info("TaskController - getTaskById:{}", id);
        return taskService.getTasksById(id)
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getPersonId(), task.getStartTime(), task.getEndTime()))
                .doOnNext(taskDto -> log.info("taskDto:{}", taskDto))
                .subscribeOn(workerPool);
    }

    @PutMapping(value = "/{id}/start", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<TaskDto> setStartTime(@PathVariable("id") Long id, @RequestBody LocalDateTime time) {
        log.info("TaskController - setStartTime:id {}, time {}", id, time);
        return taskService.setStartTime(id, time)
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getPersonId(), task.getStartTime(), task.getEndTime()))
                .doOnNext(taskDto -> log.info("taskDto:{}", taskDto))
                .subscribeOn(workerPool);
    }

    @PutMapping(value = "/{id}/end", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<TaskDto> setEndTime(@PathVariable("id") Long id, @RequestBody LocalDateTime time) {
        log.info("TaskController - setStartTime:id {}, time {}", id, time);
        return taskService.setEndTime(id, time)
                .map(task -> new TaskDto(task.getId(), task.getName(), task.getDescription(), task.getPersonId(), task.getStartTime(), task.getEndTime()))
                .doOnNext(taskDto -> log.info("taskDto:{}", taskDto))
                .subscribeOn(workerPool);
    }
}
