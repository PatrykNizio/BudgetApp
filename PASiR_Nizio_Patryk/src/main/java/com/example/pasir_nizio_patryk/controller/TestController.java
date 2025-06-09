package com.example.pasir_nizio_patryk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.pasir_nizio_patryk.model.AppInfo;

@RestController
public class TestController {
    @GetMapping("/api/test")
    public String test() {
        return "Hello World";
    }
    @GetMapping("/api/info")
    public AppInfo getInfo() {
        return new AppInfo("Aplikacja Budżetowa","1.0", "Witaj w aplikacji budżetowej stworzonej ze Spring Boot!");
    }

}
