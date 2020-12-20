package com.test.requestanalyser;

import com.test.requestanalyser.model.Data;
import com.test.requestanalyser.model.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/v1")
public class RequestAnalyserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestAnalyserApplication.class, args);
    }

    @PostMapping("/insert")
    public void insert(Data data) {
        if (data == null) throw new IllegalArgumentException("Data to insert sent null");
        RequestAnalyserManager.insert(data);
    }

    @GetMapping("/query")
    public Data query(Query query) {
        return RequestAnalyserManager.getData(query);
    }

}
