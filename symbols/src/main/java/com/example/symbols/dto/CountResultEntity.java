package com.example.symbols.dto;

import javax.persistence.*;


@Entity
@Table(name = "count_result")
public class CountResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "string")
    private String inputString;

    @Column(name = "symbol")
    private char symbol;

    @Column(name = "count")
    private int count;

    @Column(name = "request_count")
    @Transient
    private int requestCount;

    public CountResultEntity() {
    }

    public CountResultEntity(String inputString, char symbol, int count, int requestCount) {
        this.inputString = inputString;
        this.symbol = symbol;
        this.count = count;
        this.requestCount = requestCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
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

