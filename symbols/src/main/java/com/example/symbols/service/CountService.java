package com.example.symbols.service;

import org.springframework.stereotype.Service;

@Service
public class CountService {
    public int count(String string, char symbol) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == symbol) {
                count++;
            }
        }
        return count;
    }
}
