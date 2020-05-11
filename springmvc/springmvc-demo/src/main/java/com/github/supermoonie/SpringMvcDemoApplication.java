package com.github.supermoonie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author supermoonie
 * @date 2020-04-04
 */
@SpringBootApplication
@RestController
@RequestMapping("/t")
public class SpringMvcDemoApplication {

    public static void main(String[] args) {
        new SpringApplication(SpringMvcDemoApplication.class).run(args);
    }

    @RequestMapping("/t")
    public String t(@DateTimeFormat(pattern = "yyyy-MM-dd") String t) {
        System.out.println(t);
        return t;
    }
}
