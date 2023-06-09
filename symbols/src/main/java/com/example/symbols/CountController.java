package com.example.symbols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Map;

@RestController
public class CountController {
    private static final Logger logger = LoggerFactory.getLogger(CountController.class);

    private final CountCache cache;
    private final Counter counter;

    @Autowired
    public CountController(CountCache cache, Counter counter) {
        this.cache = cache;
        this.counter = counter;
    }

    @GetMapping("/count")
    public ResponseEntity<CountResult> count(@RequestParam("string") String string, @RequestParam("symbol") String symbol) {
        try {
            char s = symbol.charAt(0);
            int count = 0;

            // Increment counter
            int requestCount = counter.increment();

            if (string.isEmpty()) {
                logger.error("Input string is empty");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input string is empty");
            }

            // Check if result is in cache
            String cacheKey = string + symbol;
            Integer cachedCount = cache.get(cacheKey);
            if (cachedCount != null) {
                CountResult cachedResult = new CountResult(string, symbol, cachedCount, requestCount);
                logger.info("Result retrieved from cache: string={}, symbol={}, result={}, count={}", string, symbol, cachedResult, requestCount);
                return ResponseEntity.ok(cachedResult);
            }

            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == s) {
                    count++;
                }
            }
            CountResult result = new CountResult(string, symbol, count, requestCount);
            logger.info("Count request received: string={}, symbol={}, result={}, count={}", string, symbol, result, requestCount);

            // Add result to cache
            cache.put(cacheKey, count);

            return ResponseEntity.ok(result);
        } catch (MethodArgumentTypeMismatchException e) {
            logger.error("Parameter validation error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (ResponseStatusException e) {
            logger.error("Client error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Internal server error", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/cache")
    public Map<String, Integer> showCache() {
        return cache.getAll();
    }

    @PostMapping("/bulk")
    public BulkCountResult bulk(@RequestBody List<BulkParam> params) {
        List<CountResult> results = params.stream()
                .map(param -> count(param.getString(), param.getSymbol()).getBody())
                .collect(Collectors.toList());

        int requestCount = results.size();
        int totalCount = results.stream().mapToInt(CountResult::getCount).sum();
        double avgCount = results.stream().mapToDouble(CountResult::getCount).average().orElse(0);
        int maxCount = results.stream().mapToInt(CountResult::getCount).max().orElse(0);
        int minCount = results.stream().mapToInt(CountResult::getCount).min().orElse(0);

        return new BulkCountResult(results, requestCount, totalCount, avgCount, maxCount, minCount);
    }
}
