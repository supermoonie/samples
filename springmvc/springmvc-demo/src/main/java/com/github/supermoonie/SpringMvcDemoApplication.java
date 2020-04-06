package com.github.supermoonie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author supermoonie
 * @date 2020-04-04
 */
@SpringBootApplication
public class SpringMvcDemoApplication {

    public static void main(String[] args) {
        new SpringApplication(SpringMvcDemoApplication.class).run(args);
    }
}
