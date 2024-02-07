package mi.sosnovskaya.services;

import mi.sosnovskaya.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    public Mono<Person> savePerson(Person person);

    public Mono<Person> getPerson(Long id);

    public Flux<Person> getPersons();
}
