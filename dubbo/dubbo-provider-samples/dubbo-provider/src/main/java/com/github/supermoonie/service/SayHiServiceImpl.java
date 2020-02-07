package com.github.supermoonie.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author supermoonie
 * @since 2020-02-07
 */
@Component
@Service
public class SayHiServiceImpl implements SayHiService {

    @Override
    public String sayHi(String name) {
        return "hi " + name;
    }
}
