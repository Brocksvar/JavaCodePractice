package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping("/")
    public String home() {
        return "Welcome page!";
    }

    @GetMapping("/login")
    public String login() {
        return "message: Login page";
    }

    @GetMapping("/user/profile")
    public String userProfile(@AuthenticationPrincipal OAuth2User principal) {
        StringBuilder sb = new StringBuilder()
                .append("name: ")
                .append((String) principal.getAttribute("name"))
                .append(" | email: ")
                .append((String) principal.getAttribute("email"))
                .append(" | id: ")
                .append((String) principal.getAttribute("sub"))
                .append(" | role: ")
                .append(principal.getAuthorities().iterator().next().getAuthority());
        return sb.toString();
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(@AuthenticationPrincipal OAuth2User principal) {
        logger.info("Admin access by user: {}", principal != null ? principal.getName() : "unknown");
        return "Admin dashboard access Granted";
    }

    @GetMapping("/public/status")
    public String publicStatus() {
        return "Public access granted";
    }

    @GetMapping("/error/403")
    public String error() {
        return "error: Access denied";
    }
}
