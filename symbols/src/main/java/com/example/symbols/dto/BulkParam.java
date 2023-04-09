package com.example.symbols.dto;

public class BulkParam {
    private String string;
    private String symbol;

    public BulkParam() {
    }

    public BulkParam(String string, String symbol) {
        this.string = string;
        this.symbol = symbol;
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
}
