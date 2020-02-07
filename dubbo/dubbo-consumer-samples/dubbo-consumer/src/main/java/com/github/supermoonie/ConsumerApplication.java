package com.github.supermoonie;

import com.github.supermoonie.service.SayHiService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author supermoonie
 * @since 2020-02-07
 */
@EnableAutoConfiguration
@RestController
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Reference
    private SayHiService sayHiService;

    @GetMapping(value = "hi")
    public String hi(String name) {
        return sayHiService.sayHi(name);
    }

}
