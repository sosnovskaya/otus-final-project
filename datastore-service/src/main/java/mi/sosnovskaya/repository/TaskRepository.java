package mi.sosnovskaya.repository;

import mi.sosnovskaya.domain.Task;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TaskRepository extends ReactiveCrudRepository<Task, Long> {
    @Query("select * from task where person_id = :personId")
    Flux<Task> findByPersonId(@Param("personId") Long personId);

}
