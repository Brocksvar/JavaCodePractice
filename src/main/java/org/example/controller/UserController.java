package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of("message", "Go to /oauth2/authorization/google to login");
    }

    @GetMapping("/login")
    public Map<String, String> login() {
        return Map.of("message", "Login page");
    }

    @GetMapping("/user/profile")
    public Map<String, Object> userProfile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            logger.warn("Unauthorized access attempt to /user/profile");
            return Map.of("error", "Not authenticated");
        }

        logger.info("User {} accessed profile", principal.getName());

        return Map.of(
                "name", Objects.requireNonNull(principal.getAttribute("name")),
                "email", Objects.requireNonNull(principal.getAttribute("email")),
                "id", Objects.requireNonNull(principal.getAttribute("sub")),
                "picture", Objects.requireNonNull(principal.getAttribute("picture")),
                "role", principal.getAuthorities().iterator().next().getAuthority()
        );
    }

    @GetMapping("/admin/dashboard")
    public Map<String, String> adminDashboard(@AuthenticationPrincipal OAuth2User principal) {
        logger.info("Admin access by user: {}", principal != null ? principal.getName() : "unknown");
        return Map.of("message", "Admin dashboard", "access", "Granted");
    }

    @GetMapping("/public/status")
    public Map<String, String> publicStatus() {
        return Map.of("status", "Public access granted");
    }
}
