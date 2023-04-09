package com.example.symbols;

import java.util.List;

public class BulkCountResult {
    private final List<CountResult> results;
    private final int requestCount;
    private final int totalCount;
    private final double avgCount;
    private final int maxCount;
    private final int minCount;

    public BulkCountResult(List<CountResult> results, int requestCount, int totalCount, double avgCount, int maxCount, int minCount) {
        this.results = results;
        this.requestCount = requestCount;
        this.totalCount = totalCount;
        this.avgCount = avgCount;
        this.maxCount = maxCount;
        this.minCount = minCount;
    }

    public List<CountResult> getResults() {
        return results;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public double getAvgCount() {
        return avgCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getMinCount() {
        return minCount;
    }
}
