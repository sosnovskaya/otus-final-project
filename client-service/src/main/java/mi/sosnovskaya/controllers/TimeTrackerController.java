package mi.sosnovskaya.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TimeTrackerController {
    @GetMapping("/")
    public String persons() {
        return "persons";
    }

}
