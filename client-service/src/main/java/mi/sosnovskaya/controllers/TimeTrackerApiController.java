package mi.sosnovskaya.controllers;

import mi.sosnovskaya.model.Person;
import mi.sosnovskaya.services.PersonService;
import mi.sosnovskaya.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class TimeTrackerApiController {
    private static final Logger logger = LoggerFactory.getLogger(TimeTrackerApiController.class);
    private final TaskService taskService;
    private final PersonService personService;

    public TimeTrackerApiController(TaskService taskService, PersonService personService) {
        this.taskService = taskService;
        this.personService = personService;
    }

    @GetMapping("/person/{id}")
    public Mono<Person> getPerson(@PathVariable("id") Long id) {
        return personService.getPerson(id);
    }

}
