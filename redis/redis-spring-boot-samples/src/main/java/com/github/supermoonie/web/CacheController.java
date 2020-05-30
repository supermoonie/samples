package com.github.supermoonie.web;

import com.github.supermoonie.service.CacheService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author supermoonie
 * @since 2020/5/18
 */
@RestController
@RequestMapping(value = "/cache")
public class CacheController {

    @Resource
    private CacheService cacheService;

    @RequestMapping("/cacheable")
    public String cacheable(String key) {
        return cacheService.cacheable(key);
    }
}
