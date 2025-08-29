package com.codekod.rap.property_service.controller;

import com.codekod.rap.property_service.model.Test;
import com.codekod.rap.property_service.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @GetMapping("/hello")
    public String testApi(){
        return "Output from spring boot";
    }

    @GetMapping("/testdb")
    public List<Test> getAllTestData(){
        return testRepository.findAll();
    }
}
