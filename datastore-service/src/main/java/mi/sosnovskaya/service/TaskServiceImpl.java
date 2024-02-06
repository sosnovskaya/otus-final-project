package mi.sosnovskaya.service;

import mi.sosnovskaya.domain.Task;
import mi.sosnovskaya.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Flux<Task> getAllTasks() {
        log.info("TaskRepository - getAllTasks");
        return taskRepository.findAll();
    }

    @Override
    public Flux<Task> getAllTasksByPersonId(Long personId) {
        log.info("TaskRepository - getAllTasksByPersonId:{}", personId);
        return taskRepository.findByPersonId(personId);
    }

    @Override
    public Mono<Task> getTasksById(Long id) {
        log.info("TaskRepository - getTasksById:{}", id);
        return taskRepository.findById(id);
    }

    @Override
    public Mono<Task> saveTask(Task task) {
        log.info("TaskRepository - saveTask:{}", task);
        return taskRepository.save(task);
    }

    @Override
    public Mono<Task> setStartTime(Long taskId, LocalDateTime time) {
        log.info("TaskRepository - setStartTime:taskId {}, time {}", taskId, time);
        return taskRepository.findById(taskId)
                .doOnNext(task -> task.setStartTime(time))
                .flatMap(taskRepository::save);
    }

    @Override
    public Mono<Task> setEndTime(Long taskId, LocalDateTime time) {
        log.info("TaskRepository - setEndTime:taskId {}, time {}", taskId, time);
        return taskRepository.findById(taskId)
                .doOnNext(task -> task.setEndTime(time))
                .flatMap(taskRepository::save);
    }
}
