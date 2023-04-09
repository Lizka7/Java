package com.example.symbols.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountCache {
    private static final Logger logger = LoggerFactory.getLogger(CountCache.class);

    private final Map<String, Integer> cache = new HashMap<>();

    public void put(String key, int value) {
        logger.info("Adding value to cache: key={}, value={}", key, value);
        cache.put(key, value);
    }

    public Integer get(String key) {
        Integer value = cache.get(key);
        if (value != null) {
            logger.info("Retrieving value from cache: key={}, value={}", key, value);
        }
        return value;
    }

    public Map<String, Integer> getAll() {
        return new HashMap<>(cache);
    }
}
