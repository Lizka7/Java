package com.example.symbols.controller;

import com.example.symbols.dto.BulkCountResult;
import com.example.symbols.dto.BulkParam;
import com.example.symbols.dto.CountResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BulkCountController {
    private final CountController countController;

    @Autowired
    public BulkCountController(CountController countController) {
        this.countController = countController;
    }

    @PostMapping("/bulk")
    public BulkCountResult bulk(@RequestBody List<BulkParam> params) {
        List<CountResult> results = params.stream()
                .map(param -> countController.count(param.getString(), param.getSymbol()).getBody())
                .collect(Collectors.toList());

        int requestCount = results.size();
        int totalCount = results.stream().mapToInt(CountResult::getCount).sum();
        double avgCount = results.stream().mapToDouble(CountResult::getCount).average().orElse(0);
        int maxCount = results.stream().mapToInt(CountResult::getCount).max().orElse(0);
        int minCount = results.stream().mapToInt(CountResult::getCount).min().orElse(0);

        return new BulkCountResult(results, requestCount, totalCount, avgCount, maxCount, minCount);
    }
}
