package com.example.symbols;

import org.springframework.stereotype.Component;

@Component
public class Counter {
    private int count = 0;

    public synchronized int increment() {
        return ++count;
    }

    public synchronized int getCount() {
        return count;
    }
}
