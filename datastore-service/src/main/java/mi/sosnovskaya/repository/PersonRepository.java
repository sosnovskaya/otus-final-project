package mi.sosnovskaya.repository;

import mi.sosnovskaya.domain.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveCrudRepository<Person, Long> {

    @Query("select * from person where name = :name order by id")
    Flux<Person> findByName(@Param("name") String name);
}
