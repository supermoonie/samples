package com.github.supermoonie.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author supermoonie
 * @date 2020-04-05
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping(value = "index")
    String index(HttpServletRequest request) {
        System.out.println(request);
        return "hello world!";
    }
}
