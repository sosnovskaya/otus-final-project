package mi.sosnovskaya.service;

import mi.sosnovskaya.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
    Flux<Person> getAllPersons();

    Mono<Person> savePerson(Person person);

    Mono<Person> getPersonById(Long id);

    Flux<Person> getPersonByName(String name);
}
