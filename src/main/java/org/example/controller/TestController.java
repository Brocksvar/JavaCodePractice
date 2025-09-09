package org.example.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public String userProfile() {
        return "Профиль пользователя";
    }

    @PostMapping("/moderate")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderateContent() {
        return "Модерация контента";
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String deleteUser(@PathVariable String id) {
        return "Пользователь " + id + " удален!";
    }

    @GetMapping("/admin/allUsers")
    @Secured("ROLE_SUPER_ADMIN")
    public String allUsers() {
        return "Список всех пользователей";
    }}
