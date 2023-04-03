package com.example.symbols;

public class CountResult {
    private String string;
    private String symbol;
    private int count;

    public CountResult(String string, String symbol, int count) {
        this.string = string;
        this.symbol = symbol;
        this.count = count;
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
}
