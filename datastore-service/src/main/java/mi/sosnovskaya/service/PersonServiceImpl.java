package mi.sosnovskaya.service;

import mi.sosnovskaya.domain.Person;
import mi.sosnovskaya.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Flux<Person> getAllPersons() {
        log.info("PersonService - getAllPersons");
        return personRepository.findAll();
    }

    @Override
    public Mono<Person> savePerson(Person person) {
        log.info("PersonService - savePerson:{}", person);
        return personRepository.save(person);
    }

    @Override
    public Mono<Person> getPersonById(Long id) {
        log.info("PersonService - getPersonById:{}", id);
        return personRepository.findById(id);
    }

    @Override
    public Flux<Person> getPersonByName(String name) {
        log.info("PersonService - getPersonByName:{}", name);
        return personRepository.findByName(name);
    }
}
