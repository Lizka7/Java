package com.example.symbols;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CountController {
    private static final Logger logger = LoggerFactory.getLogger(CountController.class);

    @GetMapping("/count")
    public ResponseEntity<CountResult> count(@RequestParam("string") String string, @RequestParam("symbol") String symbol) {
        try {
            char s = symbol.charAt(0);
            int count = 0;

            if (string.isEmpty()) {
                logger.error("Input string is empty");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Input string is empty");
            }

            for (int i = 0; i < string.length(); i++) {
                if (string.charAt(i) == s) {
                    count++;
                }
            }

            CountResult result = new CountResult(string, symbol, count);
            logger.info("Count request received: string={}, symbol={}, result={}", string, symbol, result);

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
}
