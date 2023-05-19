package com.example.symbols.dto;

public class CountResult {
    private String string;
    private String symbol;
    private int count;
    private int requestCount;


    public CountResult(String string, String symbol, int count, int requestCount) {
        this.string = string;
        this.symbol = symbol;
        this.count = count;
        this.requestCount = requestCount;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
}
