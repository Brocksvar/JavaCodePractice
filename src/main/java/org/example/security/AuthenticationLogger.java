package org.example.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationLogger {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationLogger.class);

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        logger.info("Успешная аутентификация пользователя: {}", username);
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        logger.warn("Неуспешная попытка входа для пользователя: {}", username);
    }

    @EventListener
    public void onAuthenticationFailureLocked(AuthenticationFailureLockedEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        logger.error("Аккаунт заблокирован для пользователя: {}", username);
    }
}