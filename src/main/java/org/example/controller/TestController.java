package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/home")
    public String home() {
        return "Добро пожаловать! Эта страница доступна без авторизации.";
    }

    @GetMapping("/secret")
    public String secret() {
        return "Секретная страница: только для авторизованных пользователей!";
    }
}
