package org.example.myproject.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by k on 9/22/15.
 */
@RestController
public class HelloController {

    @RequestMapping("")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
