package com.github.supermoonie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author supermoonie
 * @date 2020-04-05
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static final Queue<String> CONTENT_QUEUE = new LinkedList<>();

//    @ResponseBody
//    @GetMapping(value = "index")
//    public String index(HttpServletRequest request) {
//        System.out.println(request);
//        return "hello world!";
//    }

    @GetMapping(value = "/to/index")
    public ModelAndView toIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping(value = "/post/content")
    public String testXss(@RequestParam("content") String content) {
        return "show.html";
    }


}
