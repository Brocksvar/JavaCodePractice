package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.LoginAttemptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LoginAttemptService loginAttemptService;

    @PostMapping("/unlock/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public String unlockUser(@PathVariable UUID userId) {
        loginAttemptService.unlockAccount(userId);
        return "Пользователь разблокирован!";
    }
}

