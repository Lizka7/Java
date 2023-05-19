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
    private String string;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "count")
    private int count;

    @Column(name = "request_count")
    private int requestCount;

    public CountResultEntity() {
    }

    public CountResultEntity(String string, String symbol, int count, int requestCount) {
        this.string = string;
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
        return string;
    }

    public void setInputString(String inputString) {
        this.string = inputString;
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
