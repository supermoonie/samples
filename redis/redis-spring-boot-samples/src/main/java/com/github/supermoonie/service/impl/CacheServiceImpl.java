package com.github.supermoonie.service.impl;

import com.github.supermoonie.service.CacheService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author supermoonie
 * @since 2020/5/18
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Cacheable(cacheNames = "cacheable", key = "#key")
    public String cacheable(String key) {
        return "cacheable";
    }
}
