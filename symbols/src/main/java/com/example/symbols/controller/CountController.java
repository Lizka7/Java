package com.example.symbols.controller;

import com.example.symbols.cache.CountCache;
import com.example.symbols.dto.CountResult;
import com.example.symbols.dto.CountResultEntity;
import com.example.symbols.repository.CountResultRepository;
import com.example.symbols.service.CountService;
import com.example.symbols.util.Counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RestController
public class CountController {
    private static final Logger logger = LoggerFactory.getLogger(CountController.class);

    private final CountCache cache;
    private final CountService service;
    private final Counter counter;
    private final CountResultRepository repository;

    @Autowired
    public CountController(CountCache cache, CountService service, Counter counter, CountResultRepository repository) {
        this.cache = cache;
        this.service = service;
        this.counter = counter;
        this.repository = repository;
    }

    @GetMapping("/count")
    public ResponseEntity<CountResult> count(@RequestParam("string") String string, @RequestParam("symbol") String symbol) {
        try {
            char s = symbol.charAt(0);

            // Increment counter
            int requestCount = counter.increment();

            if (string.isEmpty()) {
                logger.error("Input string is empty");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input string is empty");
            }

            if (symbol.length() != 1) {
                logger.error("Symbol should be exactly one character long");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Symbol should be exactly one character long");
            }

            // Check if result is in cache
            //String cacheKey = string + symbol;
            Integer cachedCount = cache.get(symbol);
            if (cachedCount != null) {
                CountResult cachedResult = new CountResult(string, symbol, cachedCount, requestCount);
                logger.info("Result retrieved from cache: string={}, symbol={}, result={}, count={}", string, symbol, cachedResult, requestCount);
                return ResponseEntity.ok(cachedResult);
            }

            // Calculate count asynchronously
            CompletableFuture<Integer> countFuture = calculateCountAsync(string, s);

            // Save result to database using Hibernate
            countFuture.thenAcceptAsync(count -> {
                CountResultEntity entity = new CountResultEntity();
                entity.setInputString(string);
                entity.setSymbol(symbol);
                entity.setCount(count);
                entity.setRequestCount(requestCount);
                repository.saveAndFlush(entity);
            });

            CountResult result = new CountResult(string, symbol, 0, requestCount);
            logger.info("Count request received: string={}, symbol={}, result={}, count={}", string, symbol, result, requestCount);

            // Add result to cache
            cache.put(symbol, result.getCount());

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Internal server error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Async
    public CompletableFuture<Integer> calculateCountAsync(String string, char symbol) {
        int count = service.count(string, symbol);
        cache.put(String.valueOf(symbol), count);
        return CompletableFuture.completedFuture(count);
    }


    @GetMapping("/count/requests")
    public ResponseEntity<Integer> getCount() {
        int count = counter.getCount();
        logger.info("Request count request received: count={}", count);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/cache")
    public Map<String, Integer> showCache() {
        return cache.getAll();
    }
}
