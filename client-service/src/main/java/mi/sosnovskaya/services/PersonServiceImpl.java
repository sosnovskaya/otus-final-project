package mi.sosnovskaya.services;

import mi.sosnovskaya.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final WebClient datastoreClient;

    public PersonServiceImpl(WebClient datastoreClient) {
        this.datastoreClient = datastoreClient;
    }

    @Override
    public Mono<Person> savePerson(Person person) {
        log.info("PersonService - savePerson:{}", person);
        return datastoreClient
                .post()
                .uri("/api/persons")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(person)
                .exchangeToMono(response -> response.bodyToMono(Person.class));
    }

    @Override
    public Mono<Person> getPerson(Long id) {
        log.info("PersonService - getPerson:{}", id);
        return datastoreClient
                .get()
                .uri(String.format("/api/persons/%s", id))
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(Person.class);
                    } else {
                        return response.createException().flatMap(Mono::error);
                    }
                });
    }

    @Override
    public Flux<Person> getPersons() {
        log.info("PersonService - getPersons");
        return datastoreClient
                .get()
                .uri("/api/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(Person.class));
    }
}