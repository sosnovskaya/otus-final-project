package mi.sosnovskaya.api;

import mi.sosnovskaya.domain.Person;
import mi.sosnovskaya.domain.PersonDto;
import mi.sosnovskaya.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;
    private final Scheduler workerPool;

    public PersonController(PersonService personService, Scheduler workerPool) {
        this.personService = personService;
        this.workerPool = workerPool;
    }

    @PostMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<PersonDto> savePerson(@RequestBody PersonDto personDto) {
        return Mono.just(new Person(personDto.name()))
                .doOnNext(person -> log.info("PersonController - savePerson:{}", person))
                .flatMap(personService::savePerson)
                .publishOn(workerPool)
                .doOnNext(personSaved -> log.info("person saved:{}", personSaved))
                .map(person -> new PersonDto(person.getId(), person.getName()))
                .subscribeOn(workerPool);
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PersonDto> getAllPersons() {
        log.info("PersonController - getAllPersons");
        return personService.getAllPersons()
                .map(person -> new PersonDto(person.getId(), person.getName()))
                .doOnNext(personDto -> log.info("personDto:{}", personDto))
                .subscribeOn(workerPool);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<PersonDto> getPersonById(@PathVariable("id") Long id) {
        return Mono.just(id)
                .doOnNext(room -> log.info("PersonController - getPersonById:{}", id))
                .flatMapMany(personService::getPersonById)
                .map(person -> new PersonDto(person.getId(), person.getName()))
                .doOnNext(person -> log.info("personDto:{}", person))
                .subscribeOn(workerPool);
    }
}
